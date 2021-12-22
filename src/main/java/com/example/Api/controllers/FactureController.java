package com.example.Api.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.example.Api.models.Client;
import com.example.Api.models.Facture;
import com.example.Api.models.QuantiteProduit;
import com.example.Api.models.Produit;
import com.example.Api.payload.request.FactureRequest;
import com.example.Api.repository.ClientRepository;
import com.example.Api.services.FactureService;
import com.example.Api.services.QuantiteProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/facture")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/enregistrerFacture")
    public ResponseEntity<?> creeFacture(@RequestBody FactureRequest facture) {
        System.out.println(facture.getNomClient());
        System.out.println(facture.getNumeroFacture());
        Facture factur = new Facture();
        List<Produit> listProduits = new ArrayList<Produit>();
       

        for (int i = 0; i < facture.getProduit().size(); i++) {

            Produit produit = new Produit();
            produit.setDesignation(facture.getProduit().get(i).getDesignation());
            produit.setPrix(facture.getProduit().get(i).getPrix());
            produit.setQuantite(facture.getProduit().get(i).getQuantite());
            System.out.println(produit.getQuantite());
            listProduits.add(produit);

        }
        Client client = clientRepository.findByNom(facture.getNomClient().toLowerCase()).get();

        factur.setClient(client);
        factur.setNumeroFacture(facture.getNumeroFacture());

        return factureService.enregistrerFacture(factur,listProduits);
    }

}
