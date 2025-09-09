package com.hexaware.fastx_busticketsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;

public interface BusOpDataRepo extends JpaRepository<BusOpData,Integer>{

	 Optional<BusOpData> findByBusOpLogin(BusOpLogin login);
	 
	 Optional<BusOpData> findByBusOpLogin_Username(String username);
	 
	 Optional<BusOpData> findByEmail(String email);
	 
	

}
