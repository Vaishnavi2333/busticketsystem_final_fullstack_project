package com.hexaware.fastx_busticketsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.RouteNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IRouteService;
import org.springframework.security.core.Authentication;


import jakarta.validation.Valid;

/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:05-Sept-2025
Description:Controller Class for Route*/

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/route")
public class RouteController {
	
	@Autowired
	IRouteService service;
	
	
	
	@PreAuthorize("hasRole('BUS_OPERATOR')")
	@PostMapping("/add")
	public Route addRoute(@Valid @RequestBody RouteDto routeDto, Authentication authentication) throws BusOperatorNotFoundException {
	    return service.addRoute(routeDto, authentication);
	}

	@PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
	@PutMapping("/update")
	public Route updateRoute(@Valid @RequestBody RouteDto routeDto, Authentication authentication)
	        throws RouteNotFoundException, BusOperatorNotFoundException {
	    return service.updateRoute(routeDto, authentication);
	}

	 @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
    @DeleteMapping("/delete/{id}")
    public String deleteRoute(@PathVariable("id") int routeId) throws RouteNotFoundException {
        service.deleteRoute(routeId);
        return "Route with ID " + routeId + " deleted successfully";
    }

	
    @GetMapping("/getall")
    public List<Route> getAllRoutes() {
        return service.getAllRoutes();
    }

	 @PreAuthorize("hasAnyRole('ADMIN','USER','BUS_OPERATOR')")
    @GetMapping("/getbyid/{id}")
    public Route getRouteById(@PathVariable("id") int routeId) throws RouteNotFoundException {
        return service.getRouteById(routeId);
    }
	
	 @PreAuthorize("hasAnyRole('ADMIN','BUS_OPERATOR')")
	 @GetMapping("/getbyoperator")
	 public List<Route> getRoutesByOperatorId(Authentication authentication) 
	         throws BusOperatorNotFoundException {
	     return service.getRoutesByOperatorAuth(authentication);
	 }
	

}
