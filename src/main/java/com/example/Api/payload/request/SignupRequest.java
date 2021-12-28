package com.example.Api.payload.request;


import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nom;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String mail;
    
    @NotBlank
    @Size(min = 8, max = 80)
    private String pass;
    
    @NotBlank
    @Size(min = 3, max = 40)
    private String prenom;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	
}