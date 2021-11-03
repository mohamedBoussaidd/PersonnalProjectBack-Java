package com.example.Api.services;

import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Produit;
import com.example.Api.models.User;
import com.example.Api.payload.response.MessageResponse;
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

    public ResponseEntity<?> enregistrerProduit(Set<Produit> produits) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userImpl = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userRepository.findById(userImpl.getId());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("l'utilisateur n'existe pas"));
        }
        User userr = userRepository.findById(userImpl.getId()).get();
        userr.setProduits(produits);
        userRepository.save(userr);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre produit a ete enregistrer !"),
                HttpStatus.ACCEPTED);
    }

}
