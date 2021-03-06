package com.example.Api.controllers;

import javax.validation.Valid;

import com.example.Api.models.Entreprise;
import com.example.Api.payload.request.EntrepriseRequest;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.services.EntrepriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    
    @PostMapping("addEntreprise")
    public ResponseEntity<?> addEntreprise(@Valid @RequestBody EntrepriseRequest entrepriseRequest){

        Entreprise entreprise = new Entreprise();
        entreprise.setName(entrepriseRequest.getName());
        entreprise.setAdress(entrepriseRequest.getAdress());
        entreprise.setComplementAdresse(entrepriseRequest.getComplementAdresse());
        entreprise.setNumber(entrepriseRequest.getNumber());
        entreprise.setNumeroSiret(entrepriseRequest.getNumeroSiret());
        entreprise.setEmail(entrepriseRequest.getEmail());
        entreprise.setDescription(entrepriseRequest.getDescription());

        return entrepriseService.addEntreprise(entreprise);
    }
    /* RECUPERER TOUTE LES ENTREPRISE */
    @GetMapping("/allEntreprise")
    public ResponseEntity<?> getAllEntreprise(){

        return entrepriseService.getAll();
    }
    /* RECUPERER UNE ENTREPRISE PAR IDENTREPRISE */
    @GetMapping("/entrepriseById/{entrepriseId}")
    public ResponseEntity<?> getEntreprise(@PathVariable(value = "entrepriseId")Long entrepriseId){
        return entrepriseService.getEntreprise(entrepriseId);
    }
     // Fonction pour effecer un produit
     @DeleteMapping("/supprimerEntreprise/{idEntreprise}")
     public ResponseEntity<?> supprimerProduit(
             @PathVariable(value = "idEntreprise") Long idEntreprise) {
             
         
         // je transmet au service le produit a modifier
         return entrepriseService.supprimerEntreprise(idEntreprise);
 
     }
}
