package com.hexaware.fastx_busticketsystem.service;

import com.hexaware.fastx_busticketsystem.dto.UserLoginDto;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.entities.UserLogin;
import com.hexaware.fastx_busticketsystem.exception.InvalidCredentialsException;
import com.hexaware.fastx_busticketsystem.exception.UserAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;

public interface IUserLoginService {
    
    boolean register(UserLoginDto loginDto) throws UserAlreadyExistsException;          

   
    String login(String username, String password) throws UserNotFoundException;

    boolean existsByUsername(String username);


	Integer getUserIdByUsername(String username) throws UserNotFoundException;
	
	 public void updatePassword(String username, String newPassword);
    
  
}
