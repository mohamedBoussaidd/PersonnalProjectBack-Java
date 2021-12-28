package com.example.Api.controllers;

import javax.validation.Valid;

import com.example.Api.models.Entreprise;
import com.example.Api.payload.request.EntrepriseRequest;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.services.EntrepriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/entreprise")
public class EntrepriseController {

    @Autowired
    EntrepriseService entrepriseService;

    @Autowired
    EntrepriseRepository entrepriseRepository;
    
    @PostMapping("addEntreprise")
    public ResponseEntity<?> addEntreprise(@Valid @RequestBody EntrepriseRequest entrepriseRequest){

        Entreprise entreprise = new Entreprise();
        entreprise.setName(entrepriseRequest.getName());
        entreprise.setAdress(entrepriseRequest.getAdress());
        entreprise.setNumber(entrepriseRequest.getNumber());
        entreprise.setEmail(entrepriseRequest.getEmail());
        entreprise.setDescription(entrepriseRequest.getDescription());

        return entrepriseService.addEntreprise(entreprise);
    }
    /* RECUPERER TOUTE LES ENTREPRISE */
    @GetMapping("/allEntreprise")
    public ResponseEntity<?> getAllProduits(){

        return entrepriseService.getAll();
    }
    /* RECUPERER UNE ENTREPRISE PAR IDENTREPRISE */
    @GetMapping("/entrepriseById/{entrepriseId}")
    public ResponseEntity<?> getEntreprise(@PathVariable(value = "entrepriseId")Long entrepriseId){
        return entrepriseService.getEntreprise(entrepriseId);
    }
}
