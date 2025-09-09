package com.hexaware.fastx_busticketsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.BusOpDataDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Bus Operator Data Implementation Class*/
@Service
public class BusOpDataServiceImpl implements IBusOpDataService{
	
	@Autowired
	BusOpDataRepo repo;
	
	@Autowired
	BusOpLoginRepo busOpLoginRepo;
	
	
	public BusOpDataDto mapToDTO(BusOpData entity) {
		
		BusOpDataDto dto = new BusOpDataDto();
		dto.setBusOpLoginId(entity.getBusOpId());
		dto.setName(entity.getName());
		dto.setLicenceNumber(entity.getLicenceNumber());
		dto.setGender(entity.getGender());
		dto.setEmail(entity.getEmail());
		dto.setCompanyName(entity.getCompanyName());
		dto.setDateOfBirth(entity.getDateOfBirth());
		dto.setAddress(entity.getAddress());
		dto.setContactNumber(entity.getContactNumber());
				
		return dto;
		
	}
	@Override
	public BusOpData addOperatorData(BusOpDataDto dto) throws BusOperatorNotFoundException {
		  String username = SecurityContextHolder.getContext().getAuthentication().getName();

		 
		    BusOpLogin login = busOpLoginRepo.findByUsername(username)
		            .orElseThrow(() -> new BusOperatorNotFoundException("Operator login not found"));

		   
		    BusOpData data = login.getBusopdata(); 
		    if (data == null) {
		       
		        data = new BusOpData();
		        data.setBusOpLogin(login); 
		    }

		   
		    data.setName(dto.getName());
		    data.setCompanyName(dto.getCompanyName());
		    data.setLicenceNumber(dto.getLicenceNumber());
		    data.setGender(dto.getGender());
		    data.setDateOfBirth(dto.getDateOfBirth());
		    data.setEmail(dto.getEmail());
		    data.setContactNumber(dto.getContactNumber());
		    data.setAddress(dto.getAddress());

		  
		    return repo.save(data);  
	}
	 @Override
	    public BusOpData updateOperatorData(BusOpDataDto dto) throws BusOperatorNotFoundException {
		 BusOpData existing = repo.findById(dto.getBusOpLoginId())
				    .orElseThrow(() -> new BusOperatorNotFoundException(
				        "Operator not found with id: " + dto.getBusOpLoginId()));
	        existing.setName(dto.getName());
	        existing.setGender(dto.getGender());
	        existing.setCompanyName(dto.getCompanyName());
	        existing.setLicenceNumber(dto.getLicenceNumber());
	        existing.setEmail(dto.getEmail());
	        existing.setContactNumber(dto.getContactNumber());
	        existing.setDateOfBirth(dto.getDateOfBirth());
	        existing.setAddress(dto.getAddress());

	        return repo.save(existing);
	    }

	    @Override
	    public BusOpDataDto getOperatorDataById(int operatorId) throws BusOperatorNotFoundException {
	    	BusOpData entity = repo.findById(operatorId)
	                .orElseThrow(() -> new BusOperatorNotFoundException("Operator not found"));
	        return mapToDTO(entity);
	    }

	    @Override
	    public List<BusOpDataDto> getAllOperators() {
	        return repo.findAll() .stream()
	                .map(this::mapToDTO)
	                .collect(Collectors.toList());
	    }

	    @Override
	    public String deleteOperatorData(int operatorId) throws BusOperatorNotFoundException {
	        BusOpData existing = repo.findById(operatorId)
	            .orElseThrow(() -> new BusOperatorNotFoundException("Operator not found with id: " + operatorId));
	        repo.delete(existing);
	        return "Operator with id " + operatorId + " deleted successfully.";
	    }


}
