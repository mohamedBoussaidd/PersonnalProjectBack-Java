package com.example.Api.models;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<QuantiteProduit> quantiteProduit= new ArrayList<QuantiteProduit>();


    @NotBlank
    private String designation;

    @NotNull
    private double prix;

    //ANNOTATION POUR IGNORER L'ATTRIBUT POUR L ENREGISTREMENT EN BDD
    @Transient
    private Integer quantite;

    private Integer stock;

    public Integer getQuantite() {
        return this.quantite;
    }

    

    /* CONSTRUCTEUR POUR LA CREATION DE FACTURE SANS LA GESTION DE STOCK */
    public Produit( String désignation, Integer prix, Integer quantite) {
        
        this.designation = désignation;
        this.prix = prix;
        this.quantite = quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Produit() {
    }

    public List<QuantiteProduit> getQuantiteProduit() {
        return this.quantiteProduit;
    }

    public void setQuantiteProduit(List<QuantiteProduit> quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String désignation) {
        this.designation = désignation;
    }

    public double getPrix() {
        return this.prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
