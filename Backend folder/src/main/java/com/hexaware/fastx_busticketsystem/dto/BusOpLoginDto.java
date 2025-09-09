package com.hexaware.fastx_busticketsystem.dto;

import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Bus operator login

*/

@NoArgsConstructor
@Data
public class BusOpLoginDto {

	private int busOpId;
  

    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 14, message = "Username must be between 5 and 14 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 14, message = "Password should be between 8 and 14 characters")
    private String password;
    
    private String status;

   
    public BusOpLoginDto(BusOpLogin busOpLogin) {
        this.username = busOpLogin.getUsername();
        this.busOpId = busOpLogin.getBusOpId();
        this.status = busOpLogin.getStatus() != null ? busOpLogin.getStatus().name() : null;
    }
}
