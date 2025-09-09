package com.hexaware.fastx_busticketsystem.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.fastx_busticketsystem.dto.AdminLoginDto;
import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.AdminLogin;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin.Status;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.entities.UserData;
import com.hexaware.fastx_busticketsystem.exception.AdminAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.AdminNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BookingNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.RouteNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;

public interface IAdminLoginService {
	
    public boolean registerAdmin(AdminLoginDto adminDto) throws AdminAlreadyExistsException;
    
    public String loginAdmin(String username, String password) throws AdminNotFoundException;
	
	
    
    Optional<AdminLogin> findByUsername(String username);

    public List<BusOpLoginDto> getPendingBusOperators();

	void updateBusOperatorStatus(int busOpId, Status status);
	
	public void updatePassword(String username, String newPassword);
	
	 public boolean existsByUsername(String username);
}
