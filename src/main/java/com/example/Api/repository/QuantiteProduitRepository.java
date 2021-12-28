package com.example.Api.repository;

import java.util.ArrayList;

import com.example.Api.models.QuantiteProduit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantiteProduitRepository extends JpaRepository<QuantiteProduit, Long> {
	ArrayList<QuantiteProduit> findByFactureId(Long factureId);
    
}
