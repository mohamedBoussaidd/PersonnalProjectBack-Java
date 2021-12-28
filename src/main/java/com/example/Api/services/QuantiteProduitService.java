package com.example.Api.services;

import java.util.List;

import com.example.Api.models.QuantiteProduit;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.QuantiteProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuantiteProduitService {

    @Autowired
    private QuantiteProduitRepository quantiteProduitRepository;

    public ResponseEntity<?> enregistrerQuantite(List<QuantiteProduit> listquantiteProduit) {
        System.out.println(listquantiteProduit);
        for (QuantiteProduit quantiteProduit2 : listquantiteProduit) {
            quantiteProduitRepository.save(quantiteProduit2);
            System.out.println(quantiteProduit2.getQuantite());
        }

        return new ResponseEntity<>(new MessageResponse("La quantite de produit a bien ete enregistrer"),
                HttpStatus.ACCEPTED);
    }
}
