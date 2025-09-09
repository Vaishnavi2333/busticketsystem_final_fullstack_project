package com.hexaware.fastx_busticketsystem.service;



import com.hexaware.fastx_busticketsystem.config.AppUserDetails;
import com.hexaware.fastx_busticketsystem.entities.AdminLogin;
import com.hexaware.fastx_busticketsystem.entities.BusOpLogin;
import com.hexaware.fastx_busticketsystem.entities.UserLogin;
import com.hexaware.fastx_busticketsystem.repository.AdminLoginRepo;
import com.hexaware.fastx_busticketsystem.repository.BusOpLoginRepo;
import com.hexaware.fastx_busticketsystem.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:13-Aug-2025
Description:  AppUserDetails Service Class*/
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserLoginRepo userRepo;

    @Autowired
    private BusOpLoginRepo busOpRepo;

    @Autowired
    private AdminLoginRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin user = userRepo.findByUsername(username).orElse(null);
        if (user != null) {
            return new AppUserDetails(user.getUsername(), user.getPassword(), "ROLE_USER");
        }

        BusOpLogin busOp = busOpRepo.findByUsername(username).orElse(null);
        if (busOp != null) {
            return new AppUserDetails(busOp.getUsername(), busOp.getPassword(), "ROLE_BUS_OPERATOR");
        }

        AdminLogin admin = adminRepo.findByUsername(username).orElse(null);
        if (admin != null) {
            return new AppUserDetails(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}