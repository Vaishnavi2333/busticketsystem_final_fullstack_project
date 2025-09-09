package com.hexaware.fastx_busticketsystem.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx_busticketsystem.dto.AdminLoginDto;
import com.hexaware.fastx_busticketsystem.dto.BusOpLoginDto;
import com.hexaware.fastx_busticketsystem.dto.JwtRequest;
import com.hexaware.fastx_busticketsystem.dto.UserLoginDto;
import com.hexaware.fastx_busticketsystem.exception.AdminAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.AdminNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.BusOperatorNotFoundException;
import com.hexaware.fastx_busticketsystem.exception.UserAlreadyExistsException;
import com.hexaware.fastx_busticketsystem.exception.UserNotFoundException;
import com.hexaware.fastx_busticketsystem.service.IAdminLoginService;
import com.hexaware.fastx_busticketsystem.service.IBusOpLoginService;
import com.hexaware.fastx_busticketsystem.service.IUserLoginService;

import lombok.extern.slf4j.Slf4j;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:06-Sept-2025
Description:AuthController Class*/


@CrossOrigin(origins="http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserLoginService userLoginService;

    @Autowired
    private IBusOpLoginService busOpLoginService;
    
   

    @Autowired
    private IAdminLoginService adminLoginService;

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserLoginDto dto) throws UserAlreadyExistsException {
        log.info("Received user registration request for username: ", dto.getUsername());
        userLoginService.register(dto);
        log.info("User registered successfully: ", dto.getUsername());
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/register/busoperator")
    public ResponseEntity<?> registerBusOperator(@RequestBody BusOpLoginDto dto) throws BusOperatorAlreadyExistsException {
        log.info("Received bus operator registration request for username ", dto.getUsername());
        busOpLoginService.registerBusOp(dto);
        log.info("Bus Operator registered successfully: {}", dto.getUsername());
        return ResponseEntity.ok("Bus Operator registered");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminLoginDto dto) throws AdminAlreadyExistsException {
        log.info("Received admin registration request for username ", dto.getUsername());
        adminLoginService.registerAdmin(dto);
        log.info("Admin registered successfully: ", dto.getUsername());
        return ResponseEntity.ok("Admin registered");
    }

    @PostMapping("/login/user")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody JwtRequest request) throws UserNotFoundException {
        String token = userLoginService.login(request.getUsername(), request.getPassword());
        int userId = userLoginService.getUserIdByUsername(request.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login/busoperator")
    public ResponseEntity<Map<String, Object>> loginBusOperator(@RequestBody JwtRequest request)
            throws BusOperatorNotFoundException {
        Map<String, Object> response = busOpLoginService.loginBusOperator(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<String> loginAdmin(@RequestBody JwtRequest request) throws AdminNotFoundException {
        log.info("Admin login attempt for username: ", request.getUsername());
        String token = adminLoginService.loginAdmin(request.getUsername(), request.getPassword());
        log.info("Admin login successful: ", request.getUsername());
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/forgot-password/user")
    public ResponseEntity<String> forgotPasswordUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        boolean exists = userLoginService.existsByUsername(username);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok("Username validated. Enter new password.");
    }

   
    @PostMapping("/reset-password/user")
    public ResponseEntity<String> resetPasswordUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");
        userLoginService.updatePassword(username, newPassword);
        return ResponseEntity.ok("Password reset successfully for User");
    }
    
    @PostMapping("/forgot-password/busoperator")
    public ResponseEntity<String> forgotPasswordBusOperator(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        boolean exists = busOpLoginService.existsByUsername(username);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bus Operator not found");
        }
        return ResponseEntity.ok("Username validated. Enter new password.");
    }

    @PostMapping("/reset-password/busoperator")
    public ResponseEntity<String> resetPasswordBusOperator(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");
        busOpLoginService.updatePassword(username, newPassword);
        return ResponseEntity.ok("Password reset successfully for Bus Operator");
    }
    
    @PostMapping("/forgot-password/admin")
    public ResponseEntity<String> forgotPasswordAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        boolean exists = adminLoginService.existsByUsername(username);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        return ResponseEntity.ok("Username validated. Enter new password.");
    }

    @PostMapping("/reset-password/admin")
    public ResponseEntity<String> resetPasswordAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");
        adminLoginService.updatePassword(username, newPassword);
        return ResponseEntity.ok("Password reset successfully for Admin");
    }

    
    
}
