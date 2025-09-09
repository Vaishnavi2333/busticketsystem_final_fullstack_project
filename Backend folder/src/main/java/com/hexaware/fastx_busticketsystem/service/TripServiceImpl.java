package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.TripDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;
import com.hexaware.fastx_busticketsystem.repository.TripRepo;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Trip Service Implementation Class*/
@Service
public class TripServiceImpl implements ITripService {

    @Autowired
    private TripRepo repo;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private RouteRepo routeRepo;
    
    @Autowired
    private BusOpDataRepo busOpRepo;

    @Override
    public Trip addTrip(TripDto tripDto) {
        if (tripDto.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Trip date cannot be in the past");
        }

        Trip trip = new Trip();
       // trip.setTripId(tripDto.getTripId());
        trip.setDate(tripDto.getDate());
        trip.setDepartureTime(tripDto.getDepartureTime());
        trip.setArrivalTime(tripDto.getArrivalTime());
        trip.setFare(tripDto.getFare());
        trip.setStatus(tripDto.getStatus());

        Bus bus = busRepo.findById(tripDto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + tripDto.getBusId()));
        trip.setBus(bus);

        Route route = routeRepo.findById(tripDto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + tripDto.getRouteId()));
        trip.setRoute(route);

        return repo.save(trip);
    }

    @Override
    public Trip updateTrip(TripDto tripDto) throws TripNotFoundException {
        Trip existingTrip = repo.findById(tripDto.getTripId())
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripDto.getTripId()));

        existingTrip.setDate(tripDto.getDate());
        existingTrip.setDepartureTime(tripDto.getDepartureTime());
        existingTrip.setArrivalTime(tripDto.getArrivalTime());
        existingTrip.setFare(tripDto.getFare());
        existingTrip.setStatus(tripDto.getStatus());

        
        if (tripDto.getBusId() != 0) {
            Bus bus = busRepo.findById(tripDto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + tripDto.getBusId()));
            existingTrip.setBus(bus);
        }

       
        if (tripDto.getRouteId() != 0) {
            Route route = routeRepo.findById(tripDto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found with ID: " + tripDto.getRouteId()));
            existingTrip.setRoute(route);
        }

        return repo.save(existingTrip);
    }

    @Override
    public void deleteTrip(int tripId) throws TripNotFoundException {
        Trip existingTrip = repo.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripId));
        repo.delete(existingTrip);
    }

    @Override
    public Trip getTripById(int tripId) throws TripNotFoundException {
        return repo.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripId));
    }

    @Override
    public List<TripDto> getAllTrips() {
        return repo.findAll().stream()
                   .map(TripDto::new)
                   .collect(Collectors.toList());
    }

    @Override
    public List<Trip> getTripsByRoute(int routeId) {
        return repo.findByRouteRouteId(routeId);
    }

    @Override
    public List<Trip> getTripsByBusOperator(Authentication authentication) {
        int operatorId = getOperatorIdFromAuth(authentication);

        List<Bus> buses = busRepo.findByBusOpData_BusOpLogin_BusOpId(operatorId);

        if (buses.isEmpty()) {
            return new ArrayList<>();
        }

        List<Trip> trips = new ArrayList<>();
        for (Bus bus : buses) {
            trips.addAll(repo.findByBusBusId(bus.getBusId()));
        }

        return trips;
    }
    
    @Override
    public int getOperatorIdFromAuth(Authentication authentication) {
        String username = authentication.getName(); 
        BusOpData operator = busOpRepo.findByBusOpLogin_Username(username)
            .orElseThrow(() -> new RuntimeException("Bus Operator not found for username: " + username));
        return operator.getBusOpId();
    }
    
    @Override
    public List<Trip> getTripsByBusOperator(int operatorId) {
        return repo.findByBus_BusOpData_BusOpDataId(operatorId);
    }
    
   


}
