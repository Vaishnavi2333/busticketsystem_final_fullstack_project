package com.hexaware.fastx_busticketsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.UserLoginDto;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.entities.UserLogin;
import com.hexaware.fastx_busticketsystem.exception.UserAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.UserLoginRepo;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  User Login Service Implementation Class*/
@Service
public class UserLoginServiceImpl implements IUserLoginService {

    @Autowired
    private UserLoginRepo repo;
    
    @Autowired
    private JwtService jwtservice;
    
    @Autowired
    private AppUserDetailsService appUserDetailsService;



    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Override
    public boolean register(UserLoginDto loginDto) throws UserAlreadyExistsException {

        if (repo.existsByUsername(loginDto.getUsername())) {
            throw new UserAlreadyExistsException("User '" + loginDto.getUsername() + "' already exists");
        }

        UserLogin login = new UserLogin();
        login.setUsername(loginDto.getUsername());
        
        
        login.setPassword(passwordEncoder.encode(loginDto.getPassword())); 
        
        repo.save(login);
        return true;
    }
    @Override
    public String login(String username, String password) throws UserNotFoundException {
       
        if (!repo.existsByUsername(username)) {
            throw new UserNotFoundException("User with username '" + username + "' not found");
        }

       
        UserLogin user = repo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found"));

       
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }

       
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

        
        return jwtservice.generateToken(userDetails);
    }


    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }
    
    @Override
    public Integer getUserIdByUsername(String username) throws UserNotFoundException {
        UserLogin user = repo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found"));
        return user.getUserId();  
    }
    
    @Override
    public void updatePassword(String username, String newPassword) {
        UserLogin user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);
    }
}

