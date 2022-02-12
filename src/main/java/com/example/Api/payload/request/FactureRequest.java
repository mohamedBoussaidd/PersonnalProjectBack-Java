package com.example.Api.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.Api.models.Produit;

public class FactureRequest {

    @NotNull
    private Integer numeroFacture;

    @NotNull 
    private  List<Produit> produit;

    @NotNull
    private String nomClient;

    @NotBlank
    private String numeroCommande;

    public String getNumeroCommande() {
        return this.numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }


    public String getNomClient() {
        return this.nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Integer getNumeroFacture() {
        return this.numeroFacture;
    }
   

    public void setNumeroFacture(Integer numeroFcature) {
        this.numeroFacture = numeroFcature;
    }

    public @NotNull List<Produit> getProduit() {
        return this.produit;
    }

    public void setProduit(@NotNull List<Produit> produit) {
        this.produit = produit;
    }

}
