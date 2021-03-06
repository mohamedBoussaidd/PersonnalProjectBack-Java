package com.example.Api.models;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @NotBlank(message = "Le email d'utilisateur est obligatoire")
    @Size(max = 50)
    @Email
    private String email;

    private String numero;

    private String adresse;

    private String complementAdresse;
    
    @JoinTable(name= "client_facture")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Facture> facture = new HashSet<Facture>();

    public String getComplementAdresse() {
        return this.complementAdresse;
    }

    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    public Set<Facture> getFacture() {
        return this.facture;
    }

    public void setFacture(Set<Facture> facture) {
        this.facture = facture;
    }
    


    public Client() {
    }

    public Client( String nom, String email, String adresse) {
       
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
