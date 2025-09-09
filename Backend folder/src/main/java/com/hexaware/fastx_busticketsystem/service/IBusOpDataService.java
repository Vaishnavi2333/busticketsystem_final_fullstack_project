package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.BusOpDataDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;

public interface IBusOpDataService {
	
	 BusOpData addOperatorData(BusOpDataDto dto ) throws BusOperatorNotFoundException;
	 
	 BusOpData updateOperatorData(BusOpDataDto dto) throws BusOperatorNotFoundException;
	    
	 public BusOpDataDto getOperatorDataById(int id) throws BusOperatorNotFoundException;
	    
	 List<BusOpDataDto> getAllOperators();
	    
	 public String deleteOperatorData(int operatorId) throws BusOperatorNotFoundException;

}
