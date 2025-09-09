package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for User data*/
@NoArgsConstructor
@Data
public class UserDataDto {

    
    private int userdataId;
    
    private int userId;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "[1-9][0-9]{9}", message = "Contact number must be 10 digits")
    private String contactNumber;

    @NotBlank(message="Address cannot be blank")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;
}