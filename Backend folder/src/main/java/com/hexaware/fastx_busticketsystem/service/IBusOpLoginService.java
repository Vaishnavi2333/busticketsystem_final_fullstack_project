package com.hexaware.fastx_busticketsystem.service;

import java.util.Map;

import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;

public interface IBusOpLoginService {
	
	String registerBusOp(BusOpLoginDto loginDto) throws BusOperatorAlreadyExistsException;

    String loginBusOp(String username, String password) throws BusOperatorNotFoundException;

    boolean existsByUsername(String username);

    BusOpLogin getByUsername(String username);
    public Map<String, Object> loginBusOperator(String username, String password) throws BusOperatorNotFoundException;
    
    public void updatePassword(String username, String newPassword);

}
