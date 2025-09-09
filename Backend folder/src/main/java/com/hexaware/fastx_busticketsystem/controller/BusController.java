package com.hexaware.fastx_busticketsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.BusDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IBookingService;
import com.hexaware.fastx_busticketsystem.service.IBusService;
/*
Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for Bus*/

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private IBusService service;

    @Autowired
    private IBookingService bookingService;

    @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @PostMapping("/add")
    public BusDto addBus(@Valid @RequestBody BusDto busDto) {
        return service.addBus(busDto);
    }

    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @PutMapping("/update")
    public BusDto updateBus(@Valid @RequestBody BusDto busDto) throws BusNotFoundException {
        return service.updateBus(busDto);
    }

    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteBus(@PathVariable("id") int id) throws BusNotFoundException {
        service.deleteBus(id);
        return "Bus with id " + id + " deleted successfully";
    }
    
    
    @GetMapping("/available-seats/{tripId}")
    public List<String> getAvailableSeats(@PathVariable int tripId) {
        return bookingService.getAvailableSeats(tripId);
    }

    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @GetMapping("/getbusbyid/{id}")
    public BusDto getBusById(@PathVariable("id") int id) throws BusNotFoundException {
        return service.getBusById(id);
    }

    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @GetMapping("/getallbus")
    public List<BusDto> getAllBuses() {
        return service.getAllBuses();
    }

    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @GetMapping("/getbusbyoperator/{operatorId}")
    public List<BusDto> getBusesByOperatorId(@PathVariable("operatorId") int operatorId) {
        return service.getBusesByOperatorId(operatorId);
    }

    
    @GetMapping("/search/{origin}/{destination}/{date}")
    public List<BusDto> searchBuses(@PathVariable String origin, @PathVariable String destination, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.searchBusesByOriginDestinationAndDate(origin, destination, date);
    }
}
