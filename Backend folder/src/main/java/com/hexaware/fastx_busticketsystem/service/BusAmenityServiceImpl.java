package com.hexaware.fastx_busticketsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.BusAmenityDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.BusAmenity;
import com.hexaware.fastx_busticketsystem.exception.BusAmenityNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusAmenityRepo;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Bus Amenity Service Implementation Class*/
@Service
public class BusAmenityServiceImpl implements IBusAmenityService {
	
	@Autowired
    BusAmenityRepo busAmenityRepo;

    @Autowired
    BusRepo busRepo;

    @Override
    public BusAmenity addBusAmenity(BusAmenityDto dto) throws BusNotFoundException {
        Bus bus = busRepo.findById(dto.getBusId())
                .orElseThrow(() -> new BusNotFoundException("Bus not found with id: " + dto.getBusId()));

        
        boolean exists = busAmenityRepo.existsByBus_BusIdAndAmenityNameIgnoreCase(bus.getBusId(), dto.getAmenityName());
        if (exists) {
            throw new RuntimeException("Amenity already exists for this bus");
        }

        BusAmenity ba = new BusAmenity();
        ba.setBus(bus);
        ba.setAmenityName(dto.getAmenityName());
        return busAmenityRepo.save(ba);
    }

	@Override
	public BusAmenity updateBusAmenity(BusAmenityDto busamenityDto) throws BusAmenityNotFoundException, BusNotFoundException {
		BusAmenity existing = busAmenityRepo.findById(busamenityDto.getBusId())
		        .orElseThrow(() -> new BusAmenityNotFoundException("BusAmenity not found with id: " + busamenityDto.getBusId()));

		  
		    if (busamenityDto.getBusId() != 0) {
		        Bus bus = busRepo.findById(busamenityDto.getBusId())
		            .orElseThrow(() -> new BusNotFoundException("Bus not found with id: " + busamenityDto.getBusId()));
		        existing.setBus(bus);
		    }

		    existing.setAmenityName(busamenityDto.getAmenityName());

		    return busAmenityRepo.save(existing);
	}

	 @Override
	    public void removeBusAmenity(int busAmenityId) {
	        BusAmenity ba = busAmenityRepo.findById(busAmenityId)
	                .orElseThrow(() -> new RuntimeException("BusAmenity not found with id: " + busAmenityId));
	        busAmenityRepo.delete(ba);
	    }
	@Override
	public List<BusAmenity> getAllBusAmenity() {
		return busAmenityRepo.findAll();
	}

	@Override
	public BusAmenity getBusAmenityById(int busAmenityId) {
		return busAmenityRepo.findById(busAmenityId)
	            .orElseThrow(() -> new RuntimeException("BusAmenity not found with id: " + busAmenityId));
	}
	
	@Override
	public List<BusAmenityDto> getAmenitiesByBusId(int busId) {
	    List<BusAmenity> amenities = busAmenityRepo.findByBus_BusId(busId);
	    return amenities.stream()
	                    .map(this::mapToDto)
	                    .collect(Collectors.toList());
	}

    private BusAmenityDto mapToDto(BusAmenity entity) {
        BusAmenityDto dto = new BusAmenityDto();
        dto.setBusamenityId(entity.getBusamenityId());
        dto.setAmenityName(entity.getAmenityName());
        if (entity.getBus() != null) {
            dto.setBusId(entity.getBus().getBusId());
        }
        return dto;
    }

	
}
