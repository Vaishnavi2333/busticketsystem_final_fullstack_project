package com.hexaware.fastx_busticketsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.UserData;

public interface UserDataRepo extends JpaRepository<UserData,Integer> {
	
	Optional<UserData> findByUserLogin_UserId(int userId);


}
