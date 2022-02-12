package com.example.Api.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.example.Api.models.Client;
import com.example.Api.payload.request.ClientRequest;
import com.example.Api.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("enregistrerClient/{idEntreprise}")
    public ResponseEntity<?> enregistrerClient(@Valid @RequestBody ClientRequest clients,
            @PathVariable(value = "idEntreprise") Long idEntreprise) {
                
            Client client = new Client();
            client.setNom(clients.getNom());
            client.setEmail(clients.getEmail());
            client.setNumero(clients.getNumero());
            client.setAdresse(clients.getAdresse());
            client.setComplementAdresse(clients.getComplementAdresse());
        return clientService.enregistrerClient(client, idEntreprise);
    }

    @GetMapping("/AllClient/{idEntreprise}")
    public ResponseEntity<?> getAllClient(@PathVariable(value = "idEntreprise")Long idEntreprise){

        return clientService.getAll(idEntreprise);
    }
    @PutMapping("/maJClient/{idClient}")
    public ResponseEntity<?> MiseAJourClient(@RequestBody ClientRequest clientRequest,
            @PathVariable(value = "idClient") Long idClient) {
                
        // Je cree un client
        Client client = new Client();

        // Je remplis le client vavec les données du clientRequest
        client.setNom(clientRequest.getNom());
        client.setNumero(clientRequest.getNumero());
        client.setEmail(clientRequest.getEmail());
        client.setAdresse(clientRequest.getAdresse());
        client.setComplementAdresse(clientRequest.getComplementAdresse());


        // je transmet au service le client a modifier
        return clientService.MiseAJourClient(client, idClient);

    }
    // Fonction pour effecer un produit
    @DeleteMapping("/supprimerClient/{idClient}")
    public ResponseEntity<?> supprimerClient(
            @PathVariable(value = "idClient") Long idClient) {
            
        
        // je transmet au service le client a modifier
        return clientService.supprimerClient(idClient);

    }
}
