package com.hexaware.fastx_busticketsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin.Status;

public interface BusOpLoginRepo extends JpaRepository<BusOpLogin,Integer>{
	
	
	boolean existsByUsername(String username);
	

	Optional<BusOpLogin> findByUsername(String username);


	List<BusOpLogin> findByStatus(Status status);


}
