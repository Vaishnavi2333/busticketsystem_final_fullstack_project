package com.hexaware.fastx_busticketsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBusOpLoginService;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:12-Aug-2025
Description:Controller Class for Bus operator login*/


import jakarta.validation.Valid;

@RestController
@RequestMapping("/busoplogin")
public class BusOpLoginController {
	
	@Autowired
	IBusOpLoginService service;
	
	 @PostMapping(value ="/register")
	    public String register(@RequestBody BusOpLoginDto loginDto) throws BusOperatorAlreadyExistsException {
	        service.registerBusOp(loginDto);
	        return "Bus Operator registered successfully";
	    }

	 @PostMapping("/login")
	 public String login(@RequestBody BusOpLoginDto loginDto) throws BusOperatorNotFoundException {
	     return service.loginBusOp(loginDto.getUsername(), loginDto.getPassword()); 
	    
	 }

	    @GetMapping("/exists/{username}")
	    public boolean existsByUsername(@PathVariable String username) {
	        return service.existsByUsername(username);
	    }

	    @GetMapping("getbyname/{username}")
	    public BusOpLogin getOperatorByUsername(@PathVariable String username) {
	        return service.getByUsername(username);
	    }
}
