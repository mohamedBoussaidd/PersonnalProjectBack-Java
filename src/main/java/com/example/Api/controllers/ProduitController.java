package com.example.Api.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.example.Api.models.Produit;
import com.example.Api.payload.request.ProduitRequest;
import com.example.Api.services.ProduitService;

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
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping("/enregistrerProduit/{idEntreprise}")
    public ResponseEntity<?> enregistrerProduity(@Valid @RequestBody Set<ProduitRequest> produits,@PathVariable(value = "idEntreprise")Long idEntreprise){

        Set<Produit> setDeProduits = new HashSet<Produit>();
        for(ProduitRequest element : produits){
            Produit produit = new Produit();
            produit.setDesignation(element.getDesignation());
            produit.setPrix(element.getPrix());
            produit.setStock(element.getStock());
            setDeProduits.add(produit);
        }
        return produitService.enregistrerProduit(setDeProduits,idEntreprise);

    }

    @GetMapping("/AllProduits/{idEntreprise}")
    public ResponseEntity<?> getAllProduits(@PathVariable(value = "idEntreprise")Long idEntreprise){
        return produitService.getAll(idEntreprise);
    }
}
