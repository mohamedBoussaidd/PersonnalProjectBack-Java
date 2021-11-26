package com.example.Api.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Entreprise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;

    private String adress;

    private String number;

    @NotBlank(message = "Le email de l'entreprise est obligatoire")
	@Size(max = 50)
	@Email	
    private String email;

    private String description;

    @JoinTable(name= "entreprise_Produit")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Produit> produits = new HashSet<Produit>();

    @JoinTable(name= "entreprise_Client")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Client> clients = new HashSet<Client>();

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> client) {
        for (Client client1 : client){
			this.clients.add(client1);
		};
    }

    public Set<Produit> getProduits() {
		return this.produits;
	}

	public void setProduits(Set<Produit> produit) {
		 for (Produit produit1:produit){
			this.produits.add(produit1);
		};
	} 

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  

    public Entreprise() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
