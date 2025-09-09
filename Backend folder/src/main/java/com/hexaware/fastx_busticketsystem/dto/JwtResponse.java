package com.hexaware.fastx_busticketsystem.dto;


/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:13-Aug-2025
Description:Jwt response class

*/


public class JwtResponse {
	
	
	private String token;
	public JwtResponse() { }
	public JwtResponse(String token) { 
	    	
	   this.token = token; 
	    	
	    }
	
	public String getToken() {
	    	
	   return token; 
	    	
	    }
	
   public void setToken(String token) { 
	    	
	    	this.token = token; 
	    	
	    	
	    	}

}
