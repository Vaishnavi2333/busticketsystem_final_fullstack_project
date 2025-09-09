package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.AdminLoginDto;
import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin.Status;
import com.hexaware.fastx_busticketsystem.exception.AdminAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.AdminNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IAdminLoginService;

import lombok.extern.slf4j.Slf4j;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:10-Aug-2025
Description:Controller Class for AdminLogin*/

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	  @Autowired 
	  IAdminLoginService service;
	  
	  @PostMapping("/register")
	  public String registerAdmin(@RequestBody AdminLoginDto adminDto) throws AdminAlreadyExistsException {
	      boolean registered = service.registerAdmin(adminDto);
	      if (registered) {
	          return "Admin registered successfully";
	      } else {
	          return "Admin registration failed";
	      }
	  }

	  @PostMapping("/login")
	  public String loginAdmin(@RequestParam String username, @RequestParam String password) throws AdminNotFoundException {
	      return service.loginAdmin(username, password);  
	      
	  }
	  
	  @GetMapping("/pending")
	  public List<BusOpLoginDto> getPendingBusOperators() {
	      return service.getPendingBusOperators();
	  }

	    @PutMapping("/approve/{busOpId}")
	    public ResponseEntity<String> approveBusOperator(@PathVariable int busOpId) {
	        service.updateBusOperatorStatus(busOpId, Status.APPROVED);
	        return ResponseEntity.ok("Bus Operator approved successfully");
	    }

	    @PutMapping("/reject/{busOpId}")
	    public ResponseEntity<String> rejectBusOperator(@PathVariable int busOpId) {
	        service.updateBusOperatorStatus(busOpId, Status.REJECTED);
	        return ResponseEntity.ok("Bus Operator rejected successfully");
	    }
	
}
