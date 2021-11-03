package com.example.Api.repository;

import com.example.Api.models.QuantiteProduit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantiteProduitRepository extends JpaRepository<QuantiteProduit, Long> {
    
}
