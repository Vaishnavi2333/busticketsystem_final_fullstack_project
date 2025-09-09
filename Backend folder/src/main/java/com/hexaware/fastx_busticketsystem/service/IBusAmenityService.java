package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.BusAmenityDto;
import com.hexaware.fastx_busticketsystem.entities.BusAmenity;
import com.hexaware.fastx_busticketsystem.exception.BusAmenityNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;

public interface IBusAmenityService {
	
	public BusAmenity addBusAmenity(BusAmenityDto busamenityDto) throws BusNotFoundException;
	
	public BusAmenity updateBusAmenity(BusAmenityDto busamenityDto) throws BusAmenityNotFoundException, BusNotFoundException;
	
	public void removeBusAmenity(int busAmenityId);
	
	public List<BusAmenity> getAllBusAmenity();
	
	public BusAmenity getBusAmenityById(int busAmenityId);

	List<BusAmenityDto> getAmenitiesByBusId(int busId);



	

}
