package com.example.Api.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.Api.models.Client;
import com.example.Api.models.Entreprise;
import com.example.Api.models.User;
import com.example.Api.payload.request.ClientRequest;
import com.example.Api.payload.response.MessageResponse;
import com.example.Api.repository.ClientRepository;
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

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<?> enregistrerClient(Client clients, Long idEntreprise) {

        if (clients == null) {
            return ResponseEntity.badRequest().body(new MessageResponse(" impossible d'ajouter le client !"));
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

        Set<Client> listClient = new HashSet<>();
        // POUR CHAQUE PRODUIT ENTRANT ON PARCOURT LA LISTE DE PRODUIT DE L'UTILISATEUR
        // POUR VOIR SI IL EXISTE DEJA
   
            for (Client client : entreprisee.getClients()) {
                // SI IL EXISTE DEJA ON RETOURN UNE BAD REQUEST
                if (clients.getNom().equals(client.getNom())) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Le clients ajoute existe déja"));
                }
            
            // ON AJOUTE LE PRODUIT A UNE LISTE DE PRODUITS
            listClient.add(clients);
        }
        // ON AJOUTE LA LISTE ON ENREGISTRE LES NOUVEAU PRODUITS A LA LISTE DE PRODUIT
        // DE L'ENTREPRISE ET PUIS LE L'UTILISATEUR
        entreprisee.setClients(listClient);
        entrepriseRepository.save(entreprisee);

        return new ResponseEntity<>(new MessageResponse("Félicitation votre Client a bien ete enregistrer !"),
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
        Set<ClientRequest> listClients = new HashSet<>();

        for (Client client : entreprisee.getClients()) {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setId(client.getId());
            clientRequest.setNom(client.getNom());
            clientRequest.setEmail(client.getEmail());
            clientRequest.setNumero(client.getNumero());
            clientRequest.setAdresse(client.getAdresse());
            clientRequest.setComplementAdresse(client.getComplementAdresse());
            listClients.add(clientRequest);
        }
        return ResponseEntity.ok(listClients);

    }

    // Methode pour modifier un client
    public ResponseEntity<?> MiseAJourClient(Client client, Long idClient) {
        // Je vais chercher le client correspondant a la designation du produit
        Client clientRecupererBdd = clientRepository.findById(idClient).get();

        // Je verifie qu'il ya au moins une modification dans le client si il 'yen a
        // pas je renvois un message
        if (client.getNom().equals(clientRecupererBdd.getNom()) &&
                client.getNumero().equals(clientRecupererBdd.getNumero()) &&
                client.getAdresse().equals(clientRecupererBdd.getAdresse()) &&
                client.getComplementAdresse().equals(clientRecupererBdd.getComplementAdresse()) &&
                client.getEmail().equals(clientRecupererBdd.getEmail())) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Aucun changement n'a été effectuer !"));

        } else {
            // sinon je modifie le client et je l'enregistre en BDD
            clientRecupererBdd.setNom(client.getNom());
            clientRecupererBdd.setNumero(client.getNumero());
            clientRecupererBdd.setAdresse(client.getAdresse());
            clientRecupererBdd.setComplementAdresse(client.getComplementAdresse());
            clientRecupererBdd.setEmail(client.getEmail());
            clientRepository.save(clientRecupererBdd);
            // je renvoie au front un message pour dire que le client a bien été modifier
            return new ResponseEntity<>(new MessageResponse("Le client a bien été modifier !"),
                    HttpStatus.ACCEPTED);
        }
    }

    // Methode pour supprimer un client
    public ResponseEntity<?> supprimerClient(Long idClient) {

        clientRepository.deleteById(idClient);
        // je renvoie au front un message pour dire que le client a bien été supprimer
        return new ResponseEntity<>(new MessageResponse("Le client a bien été supprimer !"),
                HttpStatus.ACCEPTED);

    }
}
