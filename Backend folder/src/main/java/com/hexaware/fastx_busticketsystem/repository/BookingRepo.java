package com.hexaware.fastx_busticketsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.fastx_busticketsystem.dto.BookingDto;
import com.hexaware.fastx_busticketsystem.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking,Integer> {
	
	List<Booking> findByUser_UserdataId(int userId);

    
    List<Booking> findByTrip_TripId(int tripId);
    
  

   

}
