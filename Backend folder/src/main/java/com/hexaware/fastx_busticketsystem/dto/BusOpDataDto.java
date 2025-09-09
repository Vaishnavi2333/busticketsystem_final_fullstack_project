package com.hexaware.fastx_busticketsystem.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:08-Aug-2025
Description:Dto Class for Bus operator data
*/

@NoArgsConstructor
@Data
public class BusOpDataDto {

    @Positive(message = "ID should be positive value")
    private Integer busOpLoginId;   

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Company Name cannot be null")
    @NotEmpty(message = "Company name cannot be empty")
    private String companyName;

    @Pattern(regexp = "[A-Z]{2}[0-9]{2}\\s[0-9]{7}", message="Invalid licence format")
    @NotNull(message = "Licence number cannot be null")
    @NotEmpty(message = "Licence number cannot be empty")
    private String licenceNumber;

    @Pattern(regexp = "Male|Female|Other")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Enter the correct email address")
    private String email;

    @Pattern(regexp = "[1-9][0-9]{9}", message="Invalid contact number")
    private String contactNumber;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;
}
