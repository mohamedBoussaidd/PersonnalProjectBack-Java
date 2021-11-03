package com.example.Api.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class QuantiteProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="facture_id")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name="produit_id")
    private Produit produit;

    @Column(name="quantite")
    private Integer quantite;



    public QuantiteProduit() {
    }
    /* CONSTRUCTEUR POUR LA CREATION DE FACTURE SANS LA GESTION DE STOCK */
    public QuantiteProduit( Facture facture, Produit produit, Integer quantite) {
        this.facture = facture;
        this.produit = produit;
        this.quantite=quantite;
    }

    public Long getId() {
        return this.id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }
    public Facture getFacture() {
        return this.facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }


}
