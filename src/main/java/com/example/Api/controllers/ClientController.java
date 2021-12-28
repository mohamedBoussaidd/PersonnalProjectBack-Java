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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> enregistrerClient(@Valid @RequestBody Set<ClientRequest> clients,
            @PathVariable(value = "idEntreprise") Long idEntreprise) {

        Set<Client> setDeClient = new HashSet<Client>();
        for (ClientRequest element : clients) {
            Client client = new Client();
            client.setNom(element.getNom());
            client.setEmail(element.getEmail());
            client.setNumero(element.getNumero());
            client.setAdresse(element.getAdresse());
            setDeClient.add(client);
        }
        return clientService.enregistrerClient(setDeClient, idEntreprise);
    }

    @GetMapping("/AllClient/{idEntreprise}")
    public ResponseEntity<?> getAllClient(@PathVariable(value = "idEntreprise")Long idEntreprise){

        return clientService.getAll(idEntreprise);
    }
}
