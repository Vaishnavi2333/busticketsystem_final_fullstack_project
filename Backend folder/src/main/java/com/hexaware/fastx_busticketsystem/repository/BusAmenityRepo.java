package com.hexaware.fastx_busticketsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.BusAmenity;

public interface BusAmenityRepo extends JpaRepository<BusAmenity,Integer> {
	 List<BusAmenity> findByBus_BusId(int busId);

	 boolean existsByBus_BusIdAndAmenityNameIgnoreCase(int busId, String amenityName);

}
