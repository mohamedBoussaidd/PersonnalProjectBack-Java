package com.example.Api.payload.response;

import java.util.List;

import com.example.Api.payload.DTO.UserDto;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private UserDto user;
	private List<String> roles;


	public JwtResponse(String accessToken, UserDto user, List<String> roles) {
		this.token = accessToken;
		this.user =user;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public List<String> getRoles() {
		return roles;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}