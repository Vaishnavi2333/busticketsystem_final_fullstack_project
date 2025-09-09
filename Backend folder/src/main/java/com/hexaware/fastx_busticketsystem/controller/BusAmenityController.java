package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.hexaware.fastx_busticketsystem.dto.BusAmenityDto;
import com.hexaware.fastx_busticketsystem.entities.BusAmenity;
import com.hexaware.fastx_busticketsystem.exception.BusAmenityNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBusAmenityService;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for BusAmenity*/

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/busamenity")
public class BusAmenityController {
	
	@Autowired
	IBusAmenityService service;
	
	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'USER')")
	    @PostMapping("/add")
	    public BusAmenity addBusAmenity(@RequestBody @Valid BusAmenityDto dto) throws BusNotFoundException {
	        return service.addBusAmenity(dto);
	    }
        
	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'USER')")
	    @PutMapping("/update")
	    public BusAmenity updateBusAmenity(@RequestBody @Valid BusAmenityDto dto) throws BusAmenityNotFoundException, BusNotFoundException {
	        return service.updateBusAmenity(dto);
	    }

	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'USER')")
	    @DeleteMapping("/remove/{id}")
	    public String removeBusAmenity(@PathVariable("id") int id) {
	        service.removeBusAmenity(id);
	        return "BusAmenity with id " + id + " deleted successfully";
	    }

	    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'USER')")
	    @GetMapping("/all")
	    public List<BusAmenity> getAllBusAmenities() {
	        return service.getAllBusAmenity();
	    }

	    @PreAuthorize("hasAnyRole('USER','BUS_OPERATOR')")
	    @GetMapping("/getbyid/{id}")
	    public BusAmenity getBusAmenityById(@PathVariable("id") int id) {
	        return service.getBusAmenityById(id);
	    }
	    
	    @PreAuthorize("hasAnyRole('USER','BUS_OPERATOR')")
	    @GetMapping("/getbybusid/{busId}")
	    public ResponseEntity<List<BusAmenityDto>> getAmenitiesByBusId(@PathVariable("busId") int busId) {
	        List<BusAmenityDto> amenities = service.getAmenitiesByBusId(busId);
	        return ResponseEntity.ok(amenities);
	    }    

}
