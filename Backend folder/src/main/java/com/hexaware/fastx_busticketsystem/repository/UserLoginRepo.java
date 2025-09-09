package com.hexaware.fastx_busticketsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.UserLogin;

public interface UserLoginRepo extends JpaRepository<UserLogin,Integer>{
	
	public Optional<UserLogin> findByUsername(String username);
	 public   boolean existsByUsername(String username);

}
