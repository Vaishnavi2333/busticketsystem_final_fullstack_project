package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.BusAmenityDto;
import com.hexaware.fastx_busticketsystem.dto.BusDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.BusAmenity;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;
import com.hexaware.fastx_busticketsystem.repository.TripRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Bus Service Implementation Class*/
@Service
public class BusServiceImpl implements IBusService {

    @Autowired
    private BusRepo repo;

    @Autowired
    private TripRepo tripRepo;
    
    @Autowired
    private RouteRepo routeRepo;
    
    @Autowired
    private BusOpDataRepo busOpDataRepo; 

   
    private BusDto mapToDto(Bus bus) {
        BusDto dto = new BusDto();
        dto.setBusId(bus.getBusId());
        dto.setBusNumber(bus.getBusNumber());
        dto.setBusName(bus.getBusName());
        dto.setBusType(bus.getBusType());
        dto.setCapacity(bus.getCapacity());
        dto.setStatus(bus.getStatus());

        dto.setAmenities(
                bus.getAmenities() != null
                    ? bus.getAmenities().stream().map(a -> {
                        BusAmenityDto adto = new BusAmenityDto();
                        adto.setBusamenityId(a.getBusamenityId());
                        adto.setAmenityName(a.getAmenityName());
                        return adto;
                    }).collect(Collectors.toList())
                    : new ArrayList<>()
            );

            return dto;
        
    }

    @Override
    public BusDto addBus(BusDto busDto) {
        Bus bus = new Bus();
        bus.setBusName(busDto.getBusName());
        bus.setBusNumber(busDto.getBusNumber());
        bus.setBusType(busDto.getBusType());
        bus.setCapacity(busDto.getCapacity());
        bus.setStatus(busDto.getStatus());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BusOpData operator = busOpDataRepo.findByBusOpLogin_Username(username)
                .orElseThrow(() -> new RuntimeException("Operator not found"));
        bus.setBusOpData(operator);

        if (busDto.getRouteId() != null) {
            Route route = routeRepo.findById(busDto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            bus.setRoute(route);
        }

        if (busDto.getTripIds() != null && !busDto.getTripIds().isEmpty()) {
            List<Trip> trips = tripRepo.findAllById(busDto.getTripIds());
            for (Trip trip : trips) {
                trip.setBus(bus);
            }
            bus.setTrips(trips);
        }

        Bus savedBus = repo.save(bus);
        return mapToDto(savedBus);
    }
    @Override
    public BusDto updateBus(BusDto busDto) throws BusNotFoundException {
        Bus existingBus = repo.findById(busDto.getBusId())
                .orElseThrow(() -> new BusNotFoundException("Bus not found with id: " + busDto.getBusId()));

        existingBus.setBusName(busDto.getBusName());
        existingBus.setBusNumber(busDto.getBusNumber());
        existingBus.setBusType(busDto.getBusType());
        existingBus.setCapacity(busDto.getCapacity());
        existingBus.setStatus(busDto.getStatus());

        

        Bus savedBus = repo.save(existingBus);
        return mapToDto(savedBus);
    }

    @Override
    public void deleteBus(int busId) throws BusNotFoundException {
        Bus existingBus = repo.findById(busId)
                .orElseThrow(() -> new BusNotFoundException("Bus not found with id: " + busId));
        repo.delete(existingBus);
    }
    @Override
    public BusDto getBusById(int busId) throws BusNotFoundException {
       
        Bus bus = repo.findById(busId)
                .orElseThrow(() -> new BusNotFoundException("Bus not found with id: " + busId));

       
        BusDto dto = mapToDto(bus);

        
        List<Trip> trips = tripRepo.findByBusBusId(busId);

        if (!trips.isEmpty()) {
          
            Trip latestTrip = trips.get(trips.size() - 1);

            dto.setFare(latestTrip.getFare());
            dto.setDepartureTime(latestTrip.getDepartureTime());
            dto.setArrivalTime(latestTrip.getArrivalTime());
        }

        return dto;
    }

    @Override
    public List<BusDto> getAllBuses() {
        return repo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BusDto> getBusesByOperatorId(int operatorId) {
        return repo.findByBusOpData_BusOpLogin_BusOpId(operatorId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BusDto> searchBusesByOriginDestinationAndDate(String origin, String destination, LocalDate date) {
        return tripRepo.findTripsByOriginDestinationAndDate(origin, destination, date).stream()
                .map(trip -> {
                    BusDto dto = mapToDto(trip.getBus()); 
                   
                    dto.setFare(trip.getFare());
                    dto.setDepartureTime(trip.getDepartureTime());
                    dto.setArrivalTime(trip.getArrivalTime());

                   
                    dto.setTripId(trip.getTripId()); 

                    return dto;
                })
                .collect(Collectors.toList());
    }
}