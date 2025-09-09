package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.TripDto;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBusOpDataService;
import com.hexaware.fastx_busticketsystem.service.ITripService;

import jakarta.validation.Valid;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for Trip*/

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    ITripService service;

    @Autowired
    IBusOpDataService operatorService;
    
    
    
    
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @PostMapping("/addtrip")
    public Trip addTrip(@Valid @RequestBody TripDto tripDto) {
        return service.addTrip(tripDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @PutMapping("/updatetrip")
    public Trip updateTrip(@Valid @RequestBody TripDto tripDto) throws TripNotFoundException {
        return service.updateTrip(tripDto);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @DeleteMapping("/delete/{tripId}")
    public String deleteTrip(@PathVariable int tripId) throws TripNotFoundException {
        service.deleteTrip(tripId);
        return "Trip with ID " + tripId + " deleted successfully.";
    }

   
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @GetMapping("/getbyid/{tripId}")
    public Trip getTripById(@PathVariable int tripId) throws TripNotFoundException {
        return service.getTripById(tripId);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR','USER')")
    @GetMapping("/getalltrips")
    public List<TripDto> getAllTrips() {
        return service.getAllTrips();
    }
    

    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR','USER')")
    @GetMapping("/route/{routeId}")
    public List<Trip> getTripsByRoute(@PathVariable int routeId) {
        return service.getTripsByRoute(routeId);
    }

    @GetMapping("/by-operator")
    public ResponseEntity<List<Trip>> getTripsByOperator(Authentication authentication) {
        
        int operatorId = service.getOperatorIdFromAuth(authentication);

       
        List<Trip> trips = service.getTripsByBusOperator(operatorId);

        return ResponseEntity.ok(trips);
    }
}
