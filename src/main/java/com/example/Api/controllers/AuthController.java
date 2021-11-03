package com.example.Api.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Api.models.ERole;
import com.example.Api.models.Role;
import com.example.Api.models.User;
import com.example.Api.payload.DTO.UserDto;
import com.example.Api.payload.request.ActivationRequest;
import com.example.Api.payload.request.LoginRequest;
import com.example.Api.payload.request.SignupRequest;
import com.example.Api.payload.response.JwtResponse;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.RoleRepository;
import com.example.Api.repository.UserRepository;
import com.example.Api.security.jwt.JwtUtils;
import com.example.Api.security.services.UserDetailsImpl;

import java.util.stream.Collectors;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	public AuthController() {
		// TODO Auto-generated constructor stub
	}

	// LES INJECTION DE DIFFERENT REPO
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private MailController mailController;

	// METHODE POUR S'AUTHENTIFIER
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// ON CREE UN OBJET AVEC LES DONNER TRANSMISE DU FRONT
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		// ON RECUPERE LE USER EN BDD ET POUR POUVOIR L INSERER DANS LE RETOUR DE LA
		// METHODE DE CONNEXION
		User user = userRepository.findById(userDetails.getId()).get();
		if (user.getConfirmer() == false) {
			return new ResponseEntity<>(
					new MessageResponse(
							"Votre compte n'est pas confirmer !! Consulter votre adresse email pour l'activer"),
					HttpStatus.BAD_REQUEST);
		}
		UserDto finalUser = new UserDto(user.getId(), user.getName(), user.getFirstname(), user.getEmail(),
				user.getDateOfBirth());

		return new ResponseEntity<>(new JwtResponse(jwt, finalUser, roles), HttpStatus.ACCEPTED);
	}

	// METHODE POUR S'INSCRIRE
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByEmail(signUpRequest.getMail())) {
			return new ResponseEntity<>(new MessageResponse("L'email indiquer est déja utilisé"), HttpStatus.CONFLICT);
		}
		String idActivation = UUID.randomUUID().toString();
		// ON CREE UN USER AVEC LES DONNER RECUPERER DU FRONT ET ON ENCODE LE MOT DE
		// PASSE
		User user = new User(signUpRequest.getNom(), signUpRequest.getPrenom(), signUpRequest.getMail(),
				encoder.encode(signUpRequest.getPass()), idActivation);
		// ON CREE UN SET DE ROLE
		Set<Role> roles = new HashSet<>();
		// ON RECUPERE LE ROLE_USER DE LA BDD
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		// ON AJOUTE LE ROLE AU USER QU ON A CREE
		user.setRoles(roles);
		// ON SAUVEGARDE LE USER EN BDD VIA LE REPO

		userRepository.save(user);
		User userr = userRepository.findByEmail(user.getEmail()).get();

		mailController.sendEmail(userr);

		return new ResponseEntity<>(new MessageResponse("Félicitation votre inscription a reussi !"),
				HttpStatus.ACCEPTED);
	}

	// FONCTION POUR ACTIVER MON COMPTE VIA MON ADRESS EMAIL
	@PostMapping("active")
	public void activer(@Valid @RequestBody ActivationRequest activation) {

		User user = userRepository.findById(activation.getId()).get();
		if (user.getId().equals(activation.getId()) && user.getIdActivation().equals(activation.getIdActivation())) {
			user.setConfirmer(true);
			userRepository.save(user);
		}
	}

}
