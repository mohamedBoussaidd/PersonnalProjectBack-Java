package com.example.Api.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Entreprise;
import com.example.Api.models.Produit;
import com.example.Api.models.User;
import com.example.Api.payload.DTO.ProduitDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProduitService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    EntrepriseRepository entrepriseRepository;

    public ResponseEntity<?> enregistrerProduit(Produit produits, Long idEntreprise) {

        if (produits == null) {
            return ResponseEntity.badRequest().body(new MessageResponse(" impossible d'ajouter le produit !"));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        // ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        // ON RECUPERER L'UTILISATEUR EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        Entreprise entreprisee = new Entreprise();

        for (Entreprise entreprise : userr.getEntreprise()) {
            if (entreprise.getId() == idEntreprise) {
                entreprisee = entreprise;
            }
        }
        Set<Produit> listProduit = new HashSet<>();
        // on verifie si le produit existe deja en bdd
        for (Produit produit2 : entreprisee.getProduits()) {
            // SI IL EXISTE DEJA ON RETOURN UNE BAD REQUEST
            if (produits.getDesignation().equals(produit2.getDesignation())) {
                return ResponseEntity.badRequest().body(new MessageResponse("le produits ajoute existe déja"));

            }
            // ON AJOUTE LE PRODUIT A UNE LISTE DE PRODUITS
            listProduit.add(produits);
        }
        // ON AJOUTE LA LISTE ON ENREGISTRE LES NOUVEAU PRODUITS A LA LISTE DE PRODUIT
        // DE L'ENTREPRISE ET PUIS LE L'UTILISATEUR
        entreprisee.setProduits(listProduit);
        entrepriseRepository.save(entreprisee);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre produit a ete enregistrer !"),
                HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> getAll(Long idEntreprise) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        // ON S'ASSURE QUE L'UTILISATEUR EXISTE
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        // ON RECUPERER L'UTILISATEUR EN BDD
        User userr = userRepository.findById(userImpl.getId()).get();

        Entreprise entreprisee = new Entreprise();

        for (Entreprise entreprise : userr.getEntreprise()) {
            if (entreprise.getId() == idEntreprise) {
                entreprisee = entreprise;
            }
        }
        Set<ProduitDto> listProduits = new HashSet<>();

        for (Produit produit : entreprisee.getProduits()) {
            ProduitDto produitDto = new ProduitDto();
            produitDto.setDesignation(produit.getDesignation());
            produitDto.setPrix(produit.getPrix());
            produitDto.setStock(produit.getStock());
            produitDto.setId(produit.getId());
            listProduits.add(produitDto);
        }
        return ResponseEntity.ok(listProduits);

    }

    // Methode pour modifier un produit
    public ResponseEntity<?> MiseAJourProduit(Produit produit, Long idProduit) {
        // Je vais chercher le produit correspondant a la designation du produit
        Produit produitRecupererBdd = produitRepository.findById(idProduit).get();

        // Je verifie qu'il ya au moins une modification dans le produit si il 'yen a
        // pas je renvois un message
        System.out.println(produit.getDesignation() + " venant du front");
        System.out.println(produitRecupererBdd.getDesignation() + " en BDD");
        System.out.println(produit.getPrix());
        System.out.println(produitRecupererBdd.getPrix());
        System.out.println(produit.getStock().intValue());
        System.out.println(produitRecupererBdd.getStock());
        if (produit.getDesignation().equals(produitRecupererBdd.getDesignation()) &&
                produit.getPrix() == produitRecupererBdd.getPrix() &&
                produit.getStock().intValue() == produitRecupererBdd.getStock().intValue()) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Aucun changement n'a été effectuer !"));

        } else {
            // sinon je modifie le produit et je l'enregistre en BDD
            produitRecupererBdd.setDesignation(produit.getDesignation());
            produitRecupererBdd.setPrix(produit.getPrix());
            produitRecupererBdd.setStock(produit.getStock());
            produitRepository.save(produitRecupererBdd);
            // je renvoie au front un message pour dire que le produit a bien été modifier
            return new ResponseEntity<>(new MessageResponse("Le produit a bien été modifier !"),
                    HttpStatus.ACCEPTED);
        }
    }

    // Methode pour supprimer un produit
    public ResponseEntity<?> supprimerProduit(Long idProduit) {

        produitRepository.deleteById(idProduit);
        // je renvoie au front un message pour dire que le produit a bien été supprimer
        return new ResponseEntity<>(new MessageResponse("Le produit a bien été supprimer !"),
                HttpStatus.ACCEPTED);

    }
}
