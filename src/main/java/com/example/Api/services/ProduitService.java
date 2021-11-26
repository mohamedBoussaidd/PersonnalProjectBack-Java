package com.example.Api.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Entreprise;
import com.example.Api.models.Produit;
import com.example.Api.models.User;
import com.example.Api.payload.request.ProduitRequest;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.EntrepriseRepository;
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
public class ProduitService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    EntrepriseRepository entrepriseRepository;


    public ResponseEntity<?> enregistrerProduit(Set<Produit> produits, Long idEntreprise) {

        if(produits.size() == 0){
            return ResponseEntity.badRequest().body(new MessageResponse(""));
        }
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


        Set<Produit> listProduit= new HashSet<>();
        // POUR CHAQUE PRODUIT ENTRANT ON PARCOURT LA LISTE DE PRODUIT DE L'UTILISATEUR POUR VOIR SI IL EXISTE DEJA
        for (Produit produit : produits) {
            for (Produit produit2 : entreprisee.getProduits()) {
                // SI IL EXISTE DEJA ON RETOURN UNE BAD REQUEST
                if (produit.getDesignation().equals(produit2.getDesignation())) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Un des produits ajoute existe déja"));
                }
            }
            // ON AJOUTE LE PRODUIT A UNE LISTE DE PRODUITS
            listProduit.add(produit);
        }
        //ON AJOUTE LA LISTE ON ENREGISTRE LES NOUVEAU PRODUITS A LA LISTE DE PRODUIT DE L'ENTREPRISE ET PUIS LE L'UTILISATEUR
        entreprisee.setProduits(listProduit);
        entrepriseRepository.save(entreprisee);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre produit a ete enregistrer !"),
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
        Set<ProduitRequest> listProduits = new HashSet<>() ;

        for(Produit produit :entreprisee.getProduits()){
            ProduitRequest produitRequest =new ProduitRequest();
            produitRequest.setDesignation(produit.getDesignation());
            produitRequest.setPrix(produit.getPrix());
            produitRequest.setStock(produit.getStock());
            listProduits.add(produitRequest);
        }
        return ResponseEntity.ok(listProduits);

    }
}
