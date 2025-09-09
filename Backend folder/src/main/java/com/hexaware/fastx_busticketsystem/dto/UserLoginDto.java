package com.hexaware.fastx_busticketsystem.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for User login
*/
@NoArgsConstructor
@Data
public class UserLoginDto {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull
    @Size(min = 8, max = 14, message = "Password should be between 8 and 14 characters")
    private String password;

    
    private String role = "ROLE_USER";
}
