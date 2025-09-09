package com.hexaware.fastx_busticketsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.fastx_busticketsystem.entities.Bus;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;

public interface BusRepo extends JpaRepository<Bus,Integer>{
	
	 List<Bus> findByBusOpData_BusOpLogin_BusOpId(int busOpId);

	@Query("SELECT b FROM Bus b JOIN b.trips t JOIN t.route r " + "WHERE r.origin = :origin AND r.destination = :destination AND t.date = :date")
		List<Bus> findBusesByOriginDestinationAndDate(@Param("origin") String origin,@Param("destination") String destination, @Param("date") LocalDate date);

	

	
}
