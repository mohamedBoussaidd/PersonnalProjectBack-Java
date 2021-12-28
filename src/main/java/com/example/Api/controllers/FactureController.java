package com.example.Api.controllers;

import java.util.ArrayList;
import java.util.List;


import com.example.Api.models.Client;
import com.example.Api.models.Facture;
import com.example.Api.models.Produit;
import com.example.Api.payload.request.FactureRequest;
import com.example.Api.repository.ClientRepository;
import com.example.Api.services.FactureService;

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
@RequestMapping("/facture")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/enregistrerFacture/{idEntreprise}")
    public ResponseEntity<?> creeFacture(@RequestBody FactureRequest facture, @PathVariable(value = "idEntreprise")Long idEntreprise) {
        System.out.println(facture.getNomClient());
        System.out.println(facture.getNumeroFacture());
        /* on cree une facture vide et une liste de produit  */
        Facture factur = new Facture();
        System.out.println(factur.getDateCreation());
        List<Produit> listProduits = new ArrayList<Produit>();
       
/* on parcour les produit de la factureRequest qu'on a en paramettre et pour chaque element on cree un produit pour le remplire avec les bonnes information du produit et on ajoute le produit a la liste de produit a chaque fois  */
        for (int i = 0; i < facture.getProduit().size(); i++) {

            Produit produit = new Produit();
            produit.setDesignation(facture.getProduit().get(i).getDesignation());
            produit.setPrix(facture.getProduit().get(i).getPrix());
            produit.setQuantite(facture.getProduit().get(i).getQuantite());
            System.out.println(produit.getQuantite());
            listProduits.add(produit);

        }
        /* on va chercher en bdd un client qui correspond au nom que l'on a dans la facrureRequest  */
        System.out.println(idEntreprise);
        Client client = clientRepository.findByNom(facture.getNomClient().toLowerCase()).get();
        
        /* on ajoute le client trouver */
        factur.setClient(client);

        /* on ajoute le numero de facture qui se trouve sur la facture Request */
        factur.setNumeroFacture(facture.getNumeroFacture());

        /* on redirige vers le service de facture avec la facture reconstruite la liste de produit et l'id de l'entreprise */
        return factureService.enregistrerFacture(factur,listProduits,idEntreprise);
    }
    @GetMapping("/AllFactures/{idEntreprise}")
    public ResponseEntity<?> getAllFactures(@PathVariable(value = "idEntreprise")Long idEntreprise){

        return factureService.getAll(idEntreprise);
    }

}
