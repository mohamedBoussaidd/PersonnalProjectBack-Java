package com.example.Api.controllers;

import java.util.List;

import com.example.Api.models.QuantiteProduit;
import com.example.Api.services.QuantiteProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class QuantiteProduitController {

    @Autowired
    QuantiteProduitService quantiteProduitService;

    public ResponseEntity<?> creeQuantitePourProduit(List<QuantiteProduit> quantiteProduit){

        return quantiteProduitService.enregistrerQuantite(quantiteProduit);
    }
    
}
