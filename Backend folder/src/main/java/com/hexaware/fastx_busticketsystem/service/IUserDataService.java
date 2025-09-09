package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.UserDataDto;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;

public interface IUserDataService {
	
	public UserData createUser(UserDataDto userDto, String username) throws UserNotFoundException;
	
    public UserData updateUser(UserDataDto userDto) throws UserNotFoundException;
    
    public String deleteUser(int userId) throws UserNotFoundException;
    
    public UserDataDto getUserById(int userId) throws UserNotFoundException;
    
    public List<UserDataDto> getAllUsers();
  
    

}
