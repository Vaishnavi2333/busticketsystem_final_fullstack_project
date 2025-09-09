package com.hexaware.fastx_busticketsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.fastx_busticketsystem.entities.Trip;

public interface TripRepo extends JpaRepository<Trip,Integer> {

	List<Trip> findByRouteRouteId(int routeId);
	
	@Query("SELECT t FROM Trip t " + "WHERE t.route.origin = :origin AND t.route.destination = :destination " + "AND t.date = :date")
	    List<Trip> findTripsByOriginDestinationAndDate( @Param("origin") String origin, @Param("destination") String destination, @Param("date") LocalDate date);
	
	 List<Trip> findByBus_BusOpData_BusOpDataId(int busOpDataId);
	
	List<Trip> findByBusBusId(int busId);
	
	    @Query("SELECT t FROM Trip t JOIN FETCH t.bus b JOIN FETCH b.route")
	    List<Trip> findAllWithBusAndRoute();



	

}
