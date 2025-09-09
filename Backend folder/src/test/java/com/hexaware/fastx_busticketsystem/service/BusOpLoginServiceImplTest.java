package com.hexaware.fastx_busticketsystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;

/*
 * Author:Vaishnavi Suresh Vaidyanath Modified Date:14-Aug-2025 Description:
 * BusOperatorLogin Class test case
 */
@SpringBootTest
@Transactional 
public class BusOpLoginServiceImplTest{

    @Autowired
    private IBusOpLoginService service;

    @Autowired
    private BusOpLoginRepo loginRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterBusOp() throws Exception {
      
        BusOpLoginDto dto = new BusOpLoginDto();
        dto.setUsername("simpleuser");
        dto.setPassword("mypassword");

        
        String response = service.registerBusOp(dto);

      
        assertThat(response).isEqualTo("Bus Operator Registered Successfully");
        BusOpLogin saved = loginRepo.findByUsername("simpleuser").orElse(null);
        assertThat(saved).isNotNull();
        assertThat(passwordEncoder.matches("mypassword", saved.getPassword())).isTrue();
    }

    @Test
    void testUpdatePassword() throws Exception {
       
        BusOpLoginDto dto = new BusOpLoginDto();
        dto.setUsername("changepass");
        dto.setPassword("oldpass");
        service.registerBusOp(dto);

      
        service.updatePassword("changepass", "newpass");

      
        BusOpLogin updated = loginRepo.findByUsername("changepass").orElse(null);
        assertThat(updated).isNotNull();
        assertThat(passwordEncoder.matches("newpass", updated.getPassword())).isTrue();
    }
}