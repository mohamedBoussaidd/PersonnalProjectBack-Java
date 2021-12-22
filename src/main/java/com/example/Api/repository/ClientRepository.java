package com.example.Api.repository;

import java.util.Optional;

import com.example.Api.models.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByNom(String nom);
    
}
