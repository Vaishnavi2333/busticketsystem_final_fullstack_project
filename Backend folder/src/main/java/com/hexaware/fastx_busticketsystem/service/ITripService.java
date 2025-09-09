package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.hexaware.fastx_busticketsystem.dto.TripDto;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;

public interface ITripService {
	
	Trip addTrip(TripDto tripDto);
	
    Trip updateTrip(TripDto tripDto) throws TripNotFoundException;
    
    void deleteTrip(int tripId) throws TripNotFoundException;
    
    Trip getTripById(int tripId) throws TripNotFoundException;
    
    public List<TripDto> getAllTrips();
    
    List<Trip> getTripsByRoute(int routeId);
    
    List<Trip> getTripsByBusOperator(Authentication authentication);

	List<Trip> getTripsByBusOperator(int operatorId);
	
	 public int getOperatorIdFromAuth(Authentication authentication);

}
