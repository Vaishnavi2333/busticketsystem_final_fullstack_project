package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.UserLoginDto;
import com.hexaware.fastx_busticketsystem.entities.UserLogin;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  UserLoginService Class test case*/
@Slf4j
@SpringBootTest
class UserLoginServiceImplTest {

    @Autowired
    IUserLoginService service;

    @Disabled
    @Test
    void testRegister() throws Exception {
        log.info("Starting testRegister...");

       
        UserLoginDto user = new UserLoginDto();
        user.setUsername("Rani");
        user.setPassword("rani12345");
       

        log.info("Registering user with username", user.getUsername());

        boolean result = service.register(user);

        log.info("Registration result", result);

        assertTrue(result, "User registration should return true");
    }

	
	@Test
    void testLogin() throws UserNotFoundException {
        log.info("Starting testLogin...");

        String username = "Rani";
        String password = "rani12345";

        log.info("Logging in user ", username);

        String token = service.login(username, password);

        log.info("Login token received: {}", token);

        assertNotNull(token, "Login should return a non-null token");
        
    }

	}
