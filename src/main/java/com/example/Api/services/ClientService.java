package com.example.Api.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Client;
import com.example.Api.models.Entreprise;
import com.example.Api.models.User;
import com.example.Api.payload.request.ClientRequest;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.EntrepriseRepository;
import com.example.Api.repository.UserRepository;
import com.example.Api.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntrepriseRepository entrepriseRepository;
    
    public ResponseEntity<?> enregistrerClient(Set<Client> clients, Long idEntreprise) {

        if(clients.size() == 0){
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

        Entreprise entreprisee = new Entreprise();
        
        for (Entreprise entreprise : userr.getEntreprise()) {
          if(entreprise.getId() == idEntreprise){
               entreprisee = entreprise;
          }
        }


        Set<Client> listClient= new HashSet<>();
        // POUR CHAQUE PRODUIT ENTRANT ON PARCOURT LA LISTE DE PRODUIT DE L'UTILISATEUR POUR VOIR SI IL EXISTE DEJA
        for (Client client : clients) {
            for (Client client2 : entreprisee.getClients()) {
                // SI IL EXISTE DEJA ON RETOURN UNE BAD REQUEST
                if (client.getNom().equals(client2.getNom())) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Un des clients ajoute existe déja"));
                }
            }
            // ON AJOUTE LE PRODUIT A UNE LISTE DE PRODUITS
            listClient.add(client);
        }
        //ON AJOUTE LA LISTE ON ENREGISTRE LES NOUVEAU PRODUITS A LA LISTE DE PRODUIT DE L'ENTREPRISE ET PUIS LE L'UTILISATEUR
        entreprisee.setClients(listClient);
        entrepriseRepository.save(entreprisee);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre Client a bien ete enregistrer !"),
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

        Entreprise entreprisee = new Entreprise();

        for (Entreprise entreprise : userr.getEntreprise()) {
            if(entreprise.getId() == idEntreprise){
                 entreprisee = entreprise;
            }
          }
        Set<ClientRequest> listClients = new HashSet<>() ;

        for(Client client :entreprisee.getClients()){
            ClientRequest clientRequest  =new ClientRequest();
            clientRequest.setId(client.getId());
            clientRequest.setNom(client.getNom());
            clientRequest.setEmail(client.getEmail());
            clientRequest.setNumero(client.getNumero());
            clientRequest.setAdresse(client.getAdresse());
            listClients.add(clientRequest);
        }
        return ResponseEntity.ok(listClients);

    }
}
