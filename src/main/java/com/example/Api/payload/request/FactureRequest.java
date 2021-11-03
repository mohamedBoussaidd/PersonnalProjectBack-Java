package com.example.Api.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.Api.models.Produit;

public class FactureRequest {

    @NotNull
    private Integer numeroFacture;

    @NotNull 
    private  List<Produit> produit;


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
