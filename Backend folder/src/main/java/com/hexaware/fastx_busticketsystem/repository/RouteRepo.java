package com.hexaware.fastx_busticketsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.Route;

public interface RouteRepo extends JpaRepository<Route,Integer>{
	
	List<Route> findByBusOpData_BusOpDataId(int operatorId);
	
	

}
