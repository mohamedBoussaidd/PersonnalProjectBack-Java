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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<?> enregistrerProduity(@Valid @RequestBody ProduitRequest produits,
            @PathVariable(value = "idEntreprise") Long idEntreprise) {

     
            Produit produit = new Produit();
            produit.setDesignation(produits.getDesignation());
            produit.setPrix(produits.getPrix());
            produit.setStock(produits.getStock());
            
        
        return produitService.enregistrerProduit(produit, idEntreprise);

    }

    @GetMapping("/AllProduits/{idEntreprise}")
    public ResponseEntity<?> getAllProduits(@PathVariable(value = "idEntreprise") Long idEntreprise) {
        return produitService.getAll(idEntreprise);
    }

    @PutMapping("/maJProduit/{idProduit}")
    public ResponseEntity<?> MiseAJourProduit(@RequestBody ProduitRequest produitRequest,
            @PathVariable(value = "idProduit") Long idProduit) {
                System.out.println(produitRequest.getDesignation());
                System.out.println(produitRequest.getPrix());
                System.out.println(produitRequest.getStock());
        // Je cree un produit
        Produit produit = new Produit();

        // Je remplis le produit vavec les données du ProduitRequest
        produit.setDesignation(produitRequest.getDesignation());
        produit.setPrix(produitRequest.getPrix());
        produit.setStock(produitRequest.getStock());
        System.out.println(produit.getDesignation());


        // je transmet au service le produit a modifier
        return produitService.MiseAJourProduit(produit, idProduit);

    }
    // Fonction pour effecer un produit
    @DeleteMapping("/supprimerProduit/{idProduit}")
    public ResponseEntity<?> supprimerProduit(
            @PathVariable(value = "idProduit") Long idProduit) {
            
        
        // je transmet au service le produit a modifier
        return produitService.supprimerProduit(idProduit);

    }

}
