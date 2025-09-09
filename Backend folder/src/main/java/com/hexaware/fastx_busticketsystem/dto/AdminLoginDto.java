package com.hexaware.fastx_busticketsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for AdminLogin*/

@NoArgsConstructor
@Data
public class AdminLoginDto {

    private int adminId;

    @NotBlank(message = "Admin name cannot be empty") 
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 14, message = "Password should be between 8 and 14 characters")
    private String password;

    
    private String role = "ROLE_ADMIN";
}