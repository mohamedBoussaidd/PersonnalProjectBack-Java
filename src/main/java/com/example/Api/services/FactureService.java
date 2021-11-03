package com.example.Api.services;

import java.util.*;

import com.example.Api.models.Facture;
import com.example.Api.models.QuantiteProduit;
import com.example.Api.models.Produit;
import com.example.Api.payload.request.FactureRequest;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.FactureRepository;
import com.example.Api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    // LES INJECTIONS
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private QuantiteProduitService quantiteProduitService;

    // ENREGISTRER UNE FACTURE
    public ResponseEntity<?> enregistrerFacture(Facture facture, List<Produit> listProduits) {

        List<QuantiteProduit> listquantiteProduit = new ArrayList<QuantiteProduit>();

        double prixHT = 0;
        double prixTTC = 0;
        for (Produit element : listProduits) {
            Produit produit = new Produit();
            QuantiteProduit quantiteProduit = new QuantiteProduit();

            // SI LE PRODUIT N' EXISTE PAS EN BDD ON L'AJOUTE
            Boolean produitExist = produitRepository.existsByDesignation(element.getDesignation());
            if (!produitExist) {
                produitRepository.save(element);
            }

            // SINON ON VA CHERCHE LE PRODUIT EN QUESTION ET ON L'AJOUTE A LA LISTE DE
            // PRODUIT D'UNE FACTURE
            produit = produitRepository.findByDesignation(element.getDesignation());


            prixHT = produit.getPrix() * element.getQuantite();
            prixTTC = (prixHT *0.20) + prixHT ;
            System.out.println(prixHT);
            System.out.println(prixTTC);
            quantiteProduit.setFacture(facture);
            quantiteProduit.setProduit(produit);
            quantiteProduit.setQuantite(produit.getQuantite());
            System.out.println(produit.getQuantite() + " dans le service facture");
            listquantiteProduit.add(quantiteProduit);

        }

        facture.setQuantiteProduit(listquantiteProduit);
        facture.setPrixTotalHT(prixHT);
        facture.setPrixTotalTTC(prixTTC);
        factureRepository.save(facture);
        quantiteProduitService.enregistrerQuantite(listquantiteProduit);
        return new ResponseEntity<>(new MessageResponse("Félicitation Votre facture a ete enregistrer !"),
                HttpStatus.ACCEPTED);
    }
}
