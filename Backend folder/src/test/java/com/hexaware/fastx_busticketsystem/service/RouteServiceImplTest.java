package com.hexaware.fastx_busticketsystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  RouteService Class test case*/

@SpringBootTest
@Transactional 
public class RouteServiceImplTest {

    @Autowired
    private IRouteService service;

    @Autowired
    private BusOpDataRepo busOpDataRepo;

    @Autowired
    private RouteRepo routeRepo;

    @Test
    void testAddRoute() throws Exception {
       
        BusOpData operator = new BusOpData();
        operator.setName("Operator One");
        busOpDataRepo.save(operator);

        Authentication auth = new TestingAuthenticationToken(operator.getBusOpLogin().getUsername(), "password");

        RouteDto dto = new RouteDto();
        dto.setRouteName("City Express");
        dto.setOrigin("City A");
        dto.setDestination("City B");
        dto.setDistanceKm(100.0);
        dto.setEstimatedTime(LocalTime.now());

     
        Route saved = service.addRoute(dto, auth);

       
        assertThat(saved.getRouteName()).isEqualTo("City Express");
    }

    @Test
    void testDeleteRoute() throws Exception {
       
        BusOpData operator = new BusOpData();
        operator.setName("Operator Two");
        busOpDataRepo.save(operator);

        Route route = new Route();
        route.setRouteName("Test Route");
        route.setOrigin("A");
        route.setDestination("B");
        route.setDistanceKm(50.0);
        route.setBusOpData(operator);
        routeRepo.save(route);

        int routeId = route.getRouteId();

      
        service.deleteRoute(routeId);

       
        assertThat(routeRepo.findById(routeId)).isEmpty();
    }
}