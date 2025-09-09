package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.RouteNotFoundException;

public interface IRouteService {
	
	
	public Route addRoute(RouteDto routeDto, Authentication authentication) throws BusOperatorNotFoundException ;
   
	 public Route updateRoute(RouteDto routeDto, Authentication authentication)
	            throws RouteNotFoundException, BusOperatorNotFoundException;
    

   
    void deleteRoute(int routeId) throws RouteNotFoundException;

    
    List<Route> getAllRoutes();

  
    Route getRouteById(int routeId) throws RouteNotFoundException;
    
    public List<Route> getRoutesByOperatorAuth(Authentication authentication) throws BusOperatorNotFoundException;

}
