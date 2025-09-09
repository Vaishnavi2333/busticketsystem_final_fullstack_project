package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.AdminLoginDto;
import com.hexaware.fastx_busticketsystem.exception.AdminAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  AdminLoginService Class test case*/


@Slf4j
@SpringBootTest
class AdminLoginServiceImplTest {
	
	@Autowired
	IAdminLoginService service;

	
	@Test
	void testRegisterAdmin() throws AdminAlreadyExistsException {
		
		log.info("Started testing...");
		
		
		AdminLoginDto adminDto = new AdminLoginDto();
	    adminDto.setUsername("TomJerry1");
	    adminDto.setPassword("tomjerry12345");
	    
	    boolean registered = service.registerAdmin(adminDto);
	    assertTrue(registered);
	}

}
