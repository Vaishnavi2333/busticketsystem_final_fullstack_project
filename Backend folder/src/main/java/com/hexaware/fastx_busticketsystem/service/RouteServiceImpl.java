package com.hexaware.fastx_busticketsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.RouteDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.Route;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.RouteNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.SameOriginDestinationException;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.RouteRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Route Service Implementation Class*/
@Service
public class RouteServiceImpl implements IRouteService {
	
	@Autowired
	RouteRepo repo;
	
	@Autowired
	BusOpDataRepo busOprepo;

	@Override
	public Route addRoute(RouteDto routeDto, Authentication authentication) throws BusOperatorNotFoundException {
	    if (routeDto.getOrigin().equalsIgnoreCase(routeDto.getDestination())) {
	        throw new SameOriginDestinationException("Origin and Destination cannot be the same!");
	    }

	    int operatorId = getOperatorIdFromAuth(authentication);

	    BusOpData busOpdata = busOprepo.findById(operatorId)
	            .orElseThrow(() -> new BusOperatorNotFoundException("Bus Operator not found with id : " + operatorId));

	    Route route = new Route();
	    route.setRouteName(routeDto.getRouteName());
	    route.setOrigin(routeDto.getOrigin());
	    route.setDestination(routeDto.getDestination());
	    route.setDistanceKm(routeDto.getDistanceKm());
	    route.setEstimatedTime(routeDto.getEstimatedTime());
	    route.setBusOpData(busOpdata);

	    return repo.save(route);
	}


	 @Override
	    public Route updateRoute(RouteDto routeDto, Authentication authentication)
	            throws RouteNotFoundException, BusOperatorNotFoundException {

	        int operatorId = getOperatorIdFromAuth(authentication);

	        Route existing = repo.findById(routeDto.getRouteId())
	                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeDto.getRouteId()));

	       
	        if (existing.getBusOpData() == null || existing.getBusOpData().getBusOpId() != operatorId) {
	            throw new BusOperatorNotFoundException("You are not allowed to update this route!");
	        }

	        existing.setRouteName(routeDto.getRouteName());
	        existing.setOrigin(routeDto.getOrigin());
	        existing.setDestination(routeDto.getDestination());
	        existing.setDistanceKm(routeDto.getDistanceKm());
	        existing.setEstimatedTime(routeDto.getEstimatedTime());

	        return repo.save(existing);
	    }
	@Override
	public void deleteRoute(int routeId) throws RouteNotFoundException {
		 Route existing = repo.findById(routeId)
		            .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));
		        repo.delete(existing);
		
	}

	@Override
	public List<Route> getAllRoutes() {
		return repo.findAll();
	}

	@Override
	public Route getRouteById(int routeId) throws RouteNotFoundException {
		 return repo.findById(routeId)
		            .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));
		    }
	
	
	
	    @Override
	    public List<Route> getRoutesByOperatorAuth(Authentication authentication) throws BusOperatorNotFoundException {
	        int operatorId = getOperatorIdFromAuth(authentication);

	        BusOpData operator = busOprepo.findById(operatorId)
	                .orElseThrow(() -> new BusOperatorNotFoundException("Bus Operator not found with id : " + operatorId));

	        return repo.findByBusOpData_BusOpDataId(operatorId); 
	    }

	   
	    private int getOperatorIdFromAuth(Authentication authentication) {
	        String username = authentication.getName(); 

	        BusOpData busOpData = busOprepo.findByBusOpLogin_Username(username)
	                .orElseThrow(() -> new RuntimeException("Bus Operator not found for username: " + username));

	        return busOpData.getBusOpId();
	    }
	
}
