package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.BusOpDataDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBusOpDataService;

import jakarta.validation.Valid;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for Bus Operator Data
*/

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/busopdata")
public class BusOpDataController {
	
	@Autowired
	IBusOpDataService service;
	
	 @PreAuthorize("hasRole('BUS_OPERATOR')")
	 @PostMapping("/add")
	 public BusOpData addOperatorData( @RequestBody @Valid BusOpDataDto dto) throws BusOperatorNotFoundException {
		    return service.addOperatorData(dto);
		}
 
	    @PreAuthorize("hasRole('BUS_OPERATOR')")
	    @PutMapping("/update")
	    public BusOpData updateOperator(@Valid @RequestBody BusOpDataDto dto) throws BusOperatorNotFoundException {
	        return service.updateOperatorData(dto);
	    }

	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
	    @GetMapping("/getbyid/{operatorId}")
	    public BusOpDataDto getOperatorById(@PathVariable int operatorId) throws BusOperatorNotFoundException {
	        return service.getOperatorDataById(operatorId);
	    }

	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
	    @GetMapping("/getall")
	    public List<BusOpDataDto> getAllOperators() {
	        
	        return service.getAllOperators(); 
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @DeleteMapping("/delete/{operatorId}")
	    public String deleteOperator(@PathVariable int operatorId) throws BusOperatorNotFoundException {
	        return service.deleteOperatorData(operatorId);
	    }

}
