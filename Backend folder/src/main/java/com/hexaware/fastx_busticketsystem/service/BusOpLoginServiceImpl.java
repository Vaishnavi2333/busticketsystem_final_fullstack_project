package com.hexaware.fastx_busticketsystem.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin.Status;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Bus Operator Login Service Implementation Class*/
	
	@Service
	public class BusOpLoginServiceImpl implements IBusOpLoginService {

	    @Autowired
	    private BusOpLoginRepo repo;

	    @Autowired
	    private JwtService jwtService;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    BusOpDataRepo busOpDataRepo;

	    @Override
	    public String registerBusOp(BusOpLoginDto loginDto) throws BusOperatorAlreadyExistsException {
	        if (existsByUsername(loginDto.getUsername())) {
	            throw new BusOperatorAlreadyExistsException("Bus operator already exists");
	        }

	        
	        BusOpLogin busOp = new BusOpLogin();
	        busOp.setUsername(loginDto.getUsername());
	        busOp.setPassword(passwordEncoder.encode(loginDto.getPassword()));

	       
	        BusOpLogin savedLogin = repo.save(busOp);

	      
	        BusOpData busOpData = new BusOpData();
	        busOpData.setBusOpLogin(savedLogin);
	       

	       
	        busOpDataRepo.save(busOpData);

	      
	        savedLogin.setBusopdata(busOpData);
	        repo.save(savedLogin);

	        return "Bus Operator Registered Successfully";
	    }


	    @Override
	    public String loginBusOp(String username, String password) throws BusOperatorNotFoundException {
	        BusOpLogin busOp = repo.findByUsername(username)
	                .orElseThrow(() -> new BusOperatorNotFoundException("Operator not found with username: " + username));

	        if (!passwordEncoder.matches(password, busOp.getPassword())) {
	            throw new BusOperatorNotFoundException("Invalid password");
	        }

	        UserDetails userDetails = User.withUsername(busOp.getUsername()).password(busOp.getPassword()).roles("BUS_OPERATOR").build();

	        return jwtService.generateToken(userDetails);
	    }

	    @Override
	    public boolean existsByUsername(String username) {
	        return repo.existsByUsername(username);
	    }

	    @Override
	    public BusOpLogin getByUsername(String username) {
	        return repo.findByUsername(username).orElse(null);
	    }
	    
	    @Override
	    public Map<String, Object> loginBusOperator(String username, String password) throws BusOperatorNotFoundException {

	        
	        BusOpLogin busOpLogin = repo.findByUsername(username)
	                .orElseThrow(() -> new BusOperatorNotFoundException("Bus Operator not found"));

	       
	        if (busOpLogin.getStatus() == Status.PENDING) { 
	            throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "Bus Operator is not approved by admin yet"
	            );
	        } else if (busOpLogin.getStatus() == Status.REJECTED) {
	            throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "Bus Operator registration has been rejected by admin"
	            );
	        }

	       
	        String token = loginBusOp(username, password);

	        int busOpId = busOpLogin.getBusOpId();

	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("busOpId", busOpId);
	        response.put("username", username);

	        return response;
	    }
	    
	    @Override
	    public void updatePassword(String username, String newPassword) {
	        BusOpLogin busOp = repo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("Bus Operator not found"));
	        busOp.setPassword(passwordEncoder.encode(newPassword)); 
	        repo.save(busOp);
	    }
	}


	


