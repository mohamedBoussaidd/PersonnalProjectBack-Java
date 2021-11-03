package com.example.Api.repository;

import java.util.Optional;

import com.example.Api.models.Produit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Boolean existsByDesignation(String designation);

    Produit findByDesignation(String designation);
}
