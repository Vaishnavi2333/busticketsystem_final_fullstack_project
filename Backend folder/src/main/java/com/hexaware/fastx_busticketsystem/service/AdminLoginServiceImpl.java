package com.hexaware.fastx_busticketsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.AdminLoginDto;
import com.hexaware.fastx_busticketsystem.dto.BusDto;
import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.AdminLogin;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin.Status;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.exception.AdminAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.AdminNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BookingNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.RouteNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.AdminLoginRepo;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;
import com.hexaware.fastx_busticketsystem.repository.UserDataRepo;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Admin login Service Implementation Class*/

@Service
public class AdminLoginServiceImpl implements IAdminLoginService {
	
	    @Autowired
	    AdminLoginRepo adminRepo;

	    @Autowired
	    RouteRepo routeRepo;

	    @Autowired
	    BusRepo busRepo;
	    
	    @Autowired
	    UserDataRepo userRepo;
	    
	    @Autowired
	    BusOpDataRepo busOpRepo;
	    
	    @Autowired
	    BookingRepo bookingRepo;
	    
	    @Autowired
	    private JwtService jwtService;

	    @Autowired
	    private AppUserDetailsService appUserDetailsService;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private BusOpLoginRepo busOpLoginRepo;

	    

	    @Override
	    public boolean registerAdmin(AdminLoginDto adminDto) throws AdminAlreadyExistsException {

	        if (adminRepo.existsByUsername(adminDto.getUsername())) {
	            throw new AdminAlreadyExistsException(
	                "Admin with username '" + adminDto.getUsername() + "' already exists"
	            );
	        }

	        AdminLogin admin = new AdminLogin();
	        admin.setUsername(adminDto.getUsername());

	        
	        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));

	        adminRepo.save(admin);
	        return true;
	    }

	    @Override
	    public String loginAdmin(String username, String password) throws AdminNotFoundException {

	       
	        AdminLogin admin = adminRepo.findByUsername(username)
	                .orElseThrow(() -> new AdminNotFoundException(
	                        "Admin with username '" + username + "' not found"));

	        
	        if (!passwordEncoder.matches(password, admin.getPassword())) {
	            throw new RuntimeException("Invalid password");
	        }

	       
	        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

	        
	        return jwtService.generateToken(userDetails);
	    }

	    @Override
	    public Optional<AdminLogin> findByUsername(String username) {
	        return adminRepo.findByUsername(username);
	    }
	  
	    @Override
	    public List<BusOpLoginDto> getPendingBusOperators() {
	        return busOpLoginRepo.findByStatus(Status.PENDING)
	                             .stream()
	                             .map(BusOpLoginDto::new)
	                             .collect(Collectors.toList());
	    }

	    @Override
	    public void updateBusOperatorStatus(int busOpId, Status status) {
	        BusOpLogin busOp = busOpLoginRepo.findById(busOpId)
	            .orElseThrow(() -> new RuntimeException("Bus Operator not found"));
	        busOp.setStatus(status);
	        busOpLoginRepo.save(busOp); 
	    }
	    
	    @Override
	    public void updatePassword(String username, String newPassword) {
	        AdminLogin admin = adminRepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("Admin not found"));
	        admin.setPassword(passwordEncoder.encode(newPassword));
	        adminRepo.save(admin);
	    }

	    @Override
	    public boolean existsByUsername(String username) {
	        return adminRepo.existsByUsername(username);
	    }
	    
	   

		
}
