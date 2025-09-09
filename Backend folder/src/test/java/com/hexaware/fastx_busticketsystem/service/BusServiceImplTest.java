package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.BusDto;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;




/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  BusService Class test case*/

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest
class BusServiceImplTest {

    @Autowired
    private IBusService busService;

    @Test
    void testAddBus() {
        log.info("Starting testAddBus...");

        BusDto busDto = new BusDto();
        busDto.setBusName("Express 1");
        busDto.setBusNumber("EXP123");
        busDto.setBusType("AC");
        busDto.setCapacity(40);
        busDto.setStatus("Active");
        busDto.setAmenities(new ArrayList<>());

        BusDto savedBus = busService.addBus(busDto);
        log.info("Added bus ID: {}", savedBus.getBusId());

        assertNotNull(savedBus, "Saved bus should not be null");
        assertEquals("EXP123", savedBus.getBusNumber(), "Bus number should match");
    }

    @Test
    void testDeleteBus() throws BusNotFoundException {
        log.info("Starting testDeleteBus...");

        int busId = 1; 
        busService.deleteBus(busId);
        log.info("Deleted bus with ID: {}", busId);

        assertThrows(Exception.class, () -> busService.getBusById(busId),
                "Bus should not exist after deletion");
    }

    @Test
    void testGetBusById() throws BusNotFoundException {
        log.info("Starting testGetBusById...");

        int busId = 1; 
        BusDto busDto = busService.getBusById(busId);

        log.info("Fetched bus name: {}", busDto.getBusName());
        assertNotNull(busDto, "Bus should not be null");
        assertEquals(busId, busDto.getBusId(), "Bus ID should match");
    }

    @Test
    void testGetAllBuses() {
        log.info("Starting testGetAllBuses...");

        List<BusDto> buses = busService.getAllBuses();
        log.info("Total buses found: {}", buses.size());

        assertNotNull(buses, "Bus list should not be null");
        assertTrue(buses.size() >= 0, "Bus list size should be zero or more");
    }
}