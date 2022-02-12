package com.example.Api.payload.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProduitDto {

    private Long id;
    
    @NotBlank

    private String designation;

    @NotNull
    private double prix;

    @NotNull
    private Integer stock;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return this.prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
