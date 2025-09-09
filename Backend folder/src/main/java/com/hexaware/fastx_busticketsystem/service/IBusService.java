package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.fastx_busticketsystem.dto.BusDto;
import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;

public interface IBusService {

    
    BusDto addBus(BusDto busDto);

    
    BusDto updateBus(BusDto busDto) throws BusNotFoundException;

   
    void deleteBus(int busId) throws BusNotFoundException;

   
    BusDto getBusById(int busId) throws BusNotFoundException;

   
    List<BusDto> getAllBuses();

  
    List<BusDto> getBusesByOperatorId(int operatorId);

   
    List<BusDto> searchBusesByOriginDestinationAndDate(String origin, String destination, LocalDate date);
}
