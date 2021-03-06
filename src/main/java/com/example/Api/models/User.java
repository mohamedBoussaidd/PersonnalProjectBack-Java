package com.example.Api.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	@NotBlank
	private String firstname;
	@NotBlank(message = "Le email d'utilisateur est obligatoire")
	@Size(max = 50)
	@Email	private String email;

	@JsonIgnore
	@NotBlank
	private String password;

	private Date dateOfBirth;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "utilisateur_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();


	@JoinTable(name= "user_Entreprise")
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Entreprise> entreprise = new HashSet<Entreprise>();


	@JsonIgnore
	@NotBlank
	private String idActivation;
	
	@JsonIgnore
	private Boolean confirmer = false;	
	
	public User() {
	}
	public User(String name,String firstname,String email,String password,String idActivation) {
		this.name = name;
		this.firstname = firstname;
		this.email=email;
		this.password= password;
		this.idActivation=idActivation;
	}

	// constructeur pour l'objet user etourne ver le front sans mot de pass etc
	public User(String name,String firstname,String email,Date dateOfBirth) {
		this.name = name;
		this.firstname = firstname;
		this.email=email;
		this.dateOfBirth = dateOfBirth;
	}

	public Set<Entreprise> getEntreprise() {
		return this.entreprise;
	}

	public void setEntreprise( Entreprise entreprise) {
		this.entreprise.add(entreprise);
	}

	public Boolean isConfirmer() {
		return this.confirmer;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdActivation() {
		return idActivation;
	}

	public void setIdActivation(String idActivation) {
		this.idActivation = idActivation;
	}

	public Boolean getConfirmer() {
		return confirmer;
	}
	public void setConfirmer(Boolean confirmer) {
		this.confirmer = confirmer;
	}

}
