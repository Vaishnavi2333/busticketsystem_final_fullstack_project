package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.BusAmenityDto;
import com.hexaware.fastx_busticketsystem.entities.BusAmenity;
import com.hexaware.fastx_busticketsystem.exception.BusNotFoundException;

import lombok.extern.slf4j.Slf4j;




/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  BusAmenityService Class test case*/

@Slf4j
@SpringBootTest
class BusAmenityServiceImplTest {

    @Autowired
    private IBusAmenityService busAmenityService;

    @Test
    void testAddBusAmenity() throws BusNotFoundException {
        log.info("Starting testAddBusAmenity...");

        BusAmenityDto dto = new BusAmenityDto();
        dto.setBusId(1);
        dto.setAmenityName("WiFi");

        log.info("Adding amenity '{} ' to bus with ID : ", dto.getAmenityName(), dto.getBusId());

        BusAmenity addedAmenity = busAmenityService.addBusAmenity(dto);

        log.info("Added BusAmenity ID: {}", addedAmenity.getBusamenityId());

        assertNotNull(addedAmenity, "Added amenity should not be null");
        assertEquals("WiFi", addedAmenity.getAmenityName(), "Amenity name should match");
    }

    @Test
    void testGetAllBusAmenity() {
        log.info("Starting testGetAllBusAmenity...");

        List<BusAmenity> amenities = busAmenityService.getAllBusAmenity();

        log.info("Total amenities found: {}", amenities.size());

        assertNotNull(amenities, "Amenity list should not be null");
        assertTrue(amenities.size() >= 0, "Amenity list size should be zero or more");
    }
}