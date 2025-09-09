package com.hexaware.fastx_busticketsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.fastx_busticketsystem.entities.AdminLogin;

public interface AdminLoginRepo extends JpaRepository<AdminLogin,Integer>{

	boolean existsByUsername(String username);

    Optional<AdminLogin> findByUsername(String username);

}
