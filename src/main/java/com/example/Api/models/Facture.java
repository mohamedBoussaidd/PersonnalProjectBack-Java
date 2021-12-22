package com.example.Api.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Facture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer numeroFacture;

    
   // @ManyToMany(fetch = FetchType.LAZY)
	//@JoinTable(	name = "facture_produit", 
	//			joinColumns = @JoinColumn(name = "facture_id"), 
	//			inverseJoinColumns = @JoinColumn(name = "produit_id"))
    //private List<Produit> produit = new ArrayList<>();
    @OneToMany(mappedBy ="facture", cascade = CascadeType.ALL)
    List<QuantiteProduit> quantiteProduit= new ArrayList<QuantiteProduit>();

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @NotNull
    private double prixTotalHT;

    @NotNull
    private double prixTotalTTC;

    public Facture() {
    }

    public Facture(Long id, Integer numeroFacture, double prixTotalHT, double prixTotalTTC, Client client) {
        this.id = id;
        this.numeroFacture = numeroFacture;
        this.prixTotalHT = prixTotalHT;
        this.prixTotalTTC = prixTotalTTC;
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFacture() {
        return this.numeroFacture;
    }

    public void setNumeroFacture(Integer numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public List<QuantiteProduit> getQuantiteProduit() {
        return this.quantiteProduit;
    }

    public void setQuantiteProduit(List<QuantiteProduit> quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public double getPrixTotalHT() {
        return this.prixTotalHT;
    }

    public void setPrixTotalHT(double prixTotalHT) {
        this.prixTotalHT = prixTotalHT;
    }

    public double getPrixTotalTTC() {
        return this.prixTotalTTC;
    }

    public void setPrixTotalTTC(double prixTotalTTC) {
        this.prixTotalTTC = prixTotalTTC;
    }
  


}
