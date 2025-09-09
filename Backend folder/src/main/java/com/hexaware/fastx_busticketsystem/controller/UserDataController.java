package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.UserDataDto;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IUserDataService;

import jakarta.validation.Valid;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for User data*/

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/userdata")
public class UserDataController {
	
	@Autowired
	IUserDataService service;
	
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/createuser")
	public UserData createUser(@Valid @RequestBody UserDataDto userDto) throws UserNotFoundException {
	  
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	   
	    return service.createUser(userDto, username);
	}
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateuser")
    public UserData updateUser(@Valid @RequestBody UserDataDto userDto) throws UserNotFoundException {
        return service.updateUser(userDto);
    }
	
	 @PreAuthorize("hasRole('ADMIN')")
	 @DeleteMapping("/deleteuser/{userId}")
	    public String deleteUser(@PathVariable int userId) throws UserNotFoundException {
	        service.deleteUser(userId);
	        return "User with ID " + userId + " deleted successfully";
	    }
	 
	 @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/allusers")
	    public List<UserDataDto> getAllUsers() {
	        return service.getAllUsers();
	    }

	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @GetMapping("/getuser/{userId}")
	    public UserDataDto getUserById(@PathVariable int userId) throws UserNotFoundException {
	        return service.getUserById(userId);
	    }

}
