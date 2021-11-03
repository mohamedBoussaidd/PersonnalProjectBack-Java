package com.example.Api.controllers;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Api.models.User;
import com.example.Api.repository.UserRepository;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository userRepo;

	

	// FONCTION POUR METTRE A JOUR LES INFORMATION D'UN UTILISATEUR
	@GetMapping("/update")
	public User updateUtilisateur(Long id, String nom, String prenom, String pass) {
		User utilisateur = userRepo.findById(id).get();
		if (nom != null) {
			utilisateur.setName(nom);
		}
		if (prenom != null) {
			utilisateur.setFirstname(prenom);
		}
		if (pass != null) {
			utilisateur.setPassword(pass);
		}
		return userRepo.save(utilisateur);
	}

}
