package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.TripDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.entities.Trip;
import com.hexaware.fastx_busticketsystem.exception.TripNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BusRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;
import com.hexaware.fastx_busticketsystem.repository.TripRepo;

import jakarta.transaction.Transactional;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  TripService Class test case*/


@SpringBootTest
@Transactional
class TripServiceImplTest {

    @Autowired
    private TripServiceImpl tripService;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private RouteRepo routeRepo;

    @Autowired
    private TripRepo tripRepo;

    private Bus testBus;
    private Route testRoute;

    @BeforeEach
    void setup() {
        
        testBus = new Bus();
        testBus.setBusId(1);
        testBus.setBusName("Test Bus");
        testBus.setCapacity(40);
        busRepo.save(testBus);

      
        testRoute = new Route();
        testRoute.setRouteId(1);
        testRoute.setRouteName("Test Route");
        testRoute.setOrigin("City A");
        testRoute.setDestination("City B");
        testRoute.setDistanceKm(150);
        testRoute.setEstimatedTime(LocalTime.of(5, 0));
        routeRepo.save(testRoute);
    }

    @Test
    void testAddTrip() {
        TripDto dto = new TripDto();
        dto.setTripId(1);
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setDepartureTime(LocalTime.of(10, 0));
        dto.setArrivalTime(LocalTime.of(15, 0));
        dto.setFare(500.0);
        dto.setStatus("Available");
        dto.setBusId(testBus.getBusId());
        dto.setRouteId(testRoute.getRouteId());

        Trip trip = tripService.addTrip(dto);

        assertNotNull(trip);
        assertEquals(dto.getTripId(), trip.getTripId());
        assertEquals("Available", trip.getStatus());
    }

    @Test
    void testUpdateTrip() throws TripNotFoundException {
       
        TripDto dto = new TripDto();
        dto.setTripId(2);
        dto.setDate(LocalDate.now().plusDays(2));
        dto.setDepartureTime(LocalTime.of(8, 0));
        dto.setArrivalTime(LocalTime.of(12, 0));
        dto.setFare(600.0);
        dto.setStatus("Available");
        dto.setBusId(testBus.getBusId());
        dto.setRouteId(testRoute.getRouteId());
        tripService.addTrip(dto);

       
        dto.setFare(650.0);
        dto.setStatus("Full");
        Trip updated = tripService.updateTrip(dto);

        assertEquals(650.0, updated.getFare());
        assertEquals("Full", updated.getStatus());
    }


    @Test
    void testGetTripById() throws TripNotFoundException {
        TripDto dto = new TripDto();
        dto.setTripId(4);
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setDepartureTime(LocalTime.of(6, 0));
        dto.setArrivalTime(LocalTime.of(11, 0));
        dto.setFare(450.0);
        dto.setStatus("Available");
        dto.setBusId(testBus.getBusId());
        dto.setRouteId(testRoute.getRouteId());
        tripService.addTrip(dto);

        Trip trip = tripService.getTripById(dto.getTripId());
        assertNotNull(trip);
        assertEquals(450.0, trip.getFare());
    }

}

   

   
