package com.example.Api.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Entreprise;
import com.example.Api.models.User;
import com.example.Api.payload.request.EntrepriseRequest;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.repository.UserRepository;
import com.example.Api.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EntrepriseService {

    @Autowired
    EntrepriseRepository entrepriseRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> addEntreprise(Entreprise entreprise) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        // ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        // ON RECUPERER L'UTILISATEUR EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        for (Entreprise entreprise1 : userr.getEntreprise()) {
            // SI ELLE EXISTE DEJA ON RETOURNE UNE BAD REQUEST
            if (entreprise.getName().equals(entreprise1.getName())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("l'entreprise que vous ajouter existe deja"));
            }
        }
        userr.setEntreprise(entreprise);
        userRepository.save(userr);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre entreprise a bien ete  enregistrer !"),
                HttpStatus.ACCEPTED);

    }

    public ResponseEntity<?> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        // ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        // ON RECUPERER L'UTILISATEUR EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        Set<EntrepriseRequest> listEntreprise = new HashSet<>();

        for (Entreprise entreprise : userr.getEntreprise()) {
            EntrepriseRequest entrepriseRequest = new EntrepriseRequest();
            entrepriseRequest.setName(entreprise.getName());
            entrepriseRequest.setAdress(entreprise.getAdress());
            entrepriseRequest.setComplementAdresse(entreprise.getComplementAdresse());
            entrepriseRequest.setEmail(entreprise.getEmail());
            entrepriseRequest.setNumber(entreprise.getNumber());
            entrepriseRequest.setNumeroSiret(entreprise.getNumeroSiret());
            entrepriseRequest.setDescription(entreprise.getDescription());
            entrepriseRequest.setId(entreprise.getId());
            listEntreprise.add(entrepriseRequest);
        }
        return ResponseEntity.ok(listEntreprise);
    }

    public ResponseEntity<?> getEntreprise(Long entrepriseId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        // ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        // ON RECUPERER L'UTILISATEUR EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        Entreprise entreprisee = new Entreprise();

        System.out.println(userr.getEntreprise());
        for (Entreprise entreprise : userr.getEntreprise()) {
            if (entreprise.getId() == entrepriseId) {
                entreprisee.setName(entreprise.getName());
                entreprisee.setAdress(entreprise.getAdress());
                entreprisee.setComplementAdresse(entreprise.getComplementAdresse());
                entreprisee.setNumber(entreprise.getNumber());
                entreprisee.setNumeroSiret(entreprise.getNumeroSiret());
                entreprisee.setDescription(entreprise.getDescription());
                entreprisee.setEmail(entreprise.getEmail());
            }
        }
        return ResponseEntity.ok(entreprisee);
    }

    // Methode pour supprimer une entreprise
    public ResponseEntity<?> supprimerEntreprise(Long idEntreprise) {

        entrepriseRepository.deleteById(idEntreprise);
        // je renvoie au front un message pour dire que l'entreprise a bien été
        // supprimer
        return new ResponseEntity<>(new MessageResponse("L'entreprise a bien été supprimer !"),
                HttpStatus.ACCEPTED);

    }
}