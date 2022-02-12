package com.example.Api.services;

import java.util.*;

import com.example.Api.models.Entreprise;
import com.example.Api.models.Facture;
import com.example.Api.models.QuantiteProduit;
import com.example.Api.models.User;
import com.example.Api.models.Produit;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.repository.FactureRepository;
import com.example.Api.repository.ProduitRepository;
import com.example.Api.repository.UserRepository;
import com.example.Api.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Autowired
    private PdfService pdfService;
    

    // ENREGISTRER UNE FACTURE
    public ResponseEntity<?> enregistrerFacture(Facture facture, List<Produit> listProduits,Long idEntreprise) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        //ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        //ON RECUPERER L'UTILISATEUR  EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        /* on cree une entreprise */
        Entreprise entreprise =new Entreprise();
        
        /* pour chaque entreprise de l'utilisateur authentifier on verifie sir l'idi de l'ntreprise correspond a l'id d'entreprise que l'on a en paramettre si il correspond on recupere l'entreprise dans la varibale d'entreprise que l'on a creé au dessus */
        for (Entreprise entreprisee : userr.getEntreprise()) {
          if(entreprisee.getId() == idEntreprise){
               entreprise = entreprisee;
          }
        }

        /* on cree une liste de quantiteProduit(table ou l'on enregistre l id de la facture avec l'id du produit et la quantite de produit)  */
        List<QuantiteProduit> listquantiteProduit = new ArrayList<QuantiteProduit>();

        /* on cree une liste de Facture */
        Set<Facture> listFacture= new HashSet<>();

        double prixHT = 0;
        double prixTTC = 0;
        /* on parcours la liste de produit que l'on a en parametre et pour chaque produit on cree un nouveau produit et un nouvelle objet de QuantiteProduit */
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

            if (produit.getStock() == 0 || produit.getStock() < element.getQuantite()) {
            return ResponseEntity.badRequest().body(new MessageResponse("LE STOCK DE "+ produit.getDesignation() +" EST INSUFFISANT "));
            }
            /* on calcule le prix HT et TTC */
            prixHT = prixHT + (produit.getPrix() * element.getQuantite());
            prixTTC =  prixHT * 1.2 ;
          
            produit.setStock( produit.getStock() - element.getQuantite());
            produitRepository.save(produit);
            /* on ajoute la facture le produit et la quantite de chaque element a l'object de QuantiteProduit pour pouvoir associer les Id dans la table */
            quantiteProduit.setFacture(facture);
            quantiteProduit.setProduit(produit);
            quantiteProduit.setQuantite(element.getQuantite());

            /* on ajoute le produit a la liste de QuantiteProduit */
            listquantiteProduit.add(quantiteProduit);

            /* on ajoute la facture a la liste de facture */
            listFacture.add(facture);

        }

        /* on ajoute la lise de QuantiteProduit,le prix HT et le prix TTC a la facture */
        facture.setQuantiteProduit(listquantiteProduit);
        facture.setPrixTotalHT(prixHT);
        facture.setPrixTotalTTC(prixTTC);
        /* on enregistre la facture en Bdd */
        factureRepository.save(facture);
        /* on modifie la liste de facture de l'entreprise en cour */
        entreprise.setFactures(listFacture);

        /* on enregistre l'entreprise en Bdd */
        entrepriseRepository.save(entreprise);
        

        /* on fait appeler au service de QuantiteProduit pour enregistrer la quantite  */
        quantiteProduitService.enregistrerQuantite(listquantiteProduit);

        pdfService.creePdf(facture, entreprise);

        return new ResponseEntity<>(new MessageResponse("Félicitation Votre facture a ete enregistrer !"),
                HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> getAll(Long idEntreprise){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        //ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        //ON RECUPERER L'UTILISATEUR  EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        Entreprise entreprisee =new Entreprise();

        for (Entreprise entreprise : userr.getEntreprise()) {
            if(entreprise.getId() == idEntreprise){
                 entreprisee = entreprise;
            }
          }
        Set<Facture> listFactures = new HashSet<>() ;

        for(Facture facture : entreprisee.getFactures()){
           
            Facture facturee = new Facture();
            facturee.setId(facture.getId());
            facturee.setNumeroFacture(facture.getNumeroFacture());
            facturee.setPrixTotalHT(facture.getPrixTotalHT());;
            facturee.setPrixTotalTTC(facture.getPrixTotalTTC());
            facturee.setClient(facture.getClient());
            facturee.setDateCreation(facture.getDateCreation());
            listFactures.add(facturee);
        }
        return ResponseEntity.ok(listFactures);
    }
}
