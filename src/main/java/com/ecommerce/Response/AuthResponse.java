package com.ecommerce.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

	
	private String jwt;
	
	private String message;
	
	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(String jwt, String message) {
		super();
		this.jwt = jwt;
		this.message = message;
	}
}
