package com.example.Api.payload.request;

public class ActivationRequest {
private Long id;
private String idActivation;

	public ActivationRequest(Long id,String idActivation) {
		this.setId(id);
		this.setIdActivation(idActivation);
	}

	public String getIdActivation() {
		return idActivation;
	}

	public void setIdActivation(String idActivation) {
		this.idActivation = idActivation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
