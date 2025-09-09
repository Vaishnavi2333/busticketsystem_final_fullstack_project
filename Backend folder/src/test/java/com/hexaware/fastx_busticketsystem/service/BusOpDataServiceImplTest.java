package com.hexaware.fastx_busticketsystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx_busticketsystem.dto.BusOpDataDto;
import com.hexaware.fastx_busticketsystem.entities.BusOpData;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.repository.BusOpDataRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;

/*
 * Author:Vaishnavi Suresh Vaidyanath Modified Date:14-Aug-2025 Description:
 * BusOperatorService Class test case
 */

@SpringBootTest
@Transactional  
public class BusOpDataServiceImplTest {

    @Autowired
    private BusOpDataServiceImpl service;

    @Autowired
    private BusOpDataRepo repo;

    @Autowired
    private BusOpLoginRepo loginRepo;

    private BusOpLogin login;

    @BeforeEach
    void setup() {
       
        login = new BusOpLogin();
        login.setUsername("testuser");
        login.setPassword("password");
        loginRepo.save(login);
    }

    @Test
    void testAddOperatorData() throws Exception {
        
        BusOpDataDto dto = new BusOpDataDto();
        dto.setName("Suresh");
        dto.setCompanyName("City Travels");
        dto.setLicenceNumber("MH03 1234567");
        dto.setGender("Male");
        dto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        dto.setEmail("suresh@example.com");
        dto.setContactNumber("9876543210");
        dto.setAddress("Chennai");

       
        BusOpData saved = service.addOperatorData(dto);

       
        assertThat(saved.getBusOpId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Suresh");
        assertThat(saved.getCompanyName()).isEqualTo("City Travels");
    }

    @Test
    void testUpdateOperatorData() throws Exception {
        
        BusOpData data = new BusOpData();
        data.setBusOpLogin(login);
        data.setName("Suresh");
        data.setCompanyName("Fast Travels");
        repo.save(data);

        BusOpDataDto updateDto = new BusOpDataDto();
        updateDto.setBusOpLoginId(data.getBusOpId());
        updateDto.setName("Riya");
        updateDto.setCompanyName("City Travels");
        updateDto.setLicenceNumber("KA03 1234567");
        updateDto.setGender("Female");
        updateDto.setEmail("ria12@gmail.com");
        updateDto.setContactNumber("1234567890");
        updateDto.setAddress("Mumbai");

        
        BusOpData updated = service.updateOperatorData(updateDto);

        
        assertThat(updated.getName()).isEqualTo("Riya");
        assertThat(updated.getCompanyName()).isEqualTo("City Travels");
        assertThat(updated.getLicenceNumber()).isEqualTo("KA03 1234567");
    }

    @Test
    void testGetOperatorDataById() throws Exception {
        
        BusOpData data = new BusOpData();
        data.setBusOpLogin(login);
        data.setName("Alice");
        data.setCompanyName("Express Bus");
        data.setLicenceNumber("KL03 1234567");
        data.setEmail("alice@example.com");
        repo.save(data);

        
        BusOpDataDto dto = service.getOperatorDataById(data.getBusOpId());

       
        assertThat(dto.getName()).isEqualTo("Alice");
       
    }
}
 