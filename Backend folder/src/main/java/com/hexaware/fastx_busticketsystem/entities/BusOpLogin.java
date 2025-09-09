package com.hexaware.fastx_busticketsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:Bus Operator login Entity Class*/

@Entity
@Table(name = "bus_op_login")
public class BusOpLogin {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "bus_op_id")
	    private int busOpId;

	    @OneToOne(mappedBy = "busOpLogin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private BusOpData busOpData;
	

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    
    public Status getStatus() {
    	return status;
    }
    
    public void setStatus(Status status) {
    	this.status = status;
    }

    
    public BusOpLogin() {
    }

    public BusOpLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BusOpLogin(String username, String password, BusOpData busOpdata) {
        this.username = username;
        this.password = password;
        this.busOpData = busOpdata;
    }

    public int getBusOpId() {
        return busOpId;
    }

    public void setBusOpId(int busOpId) {
        this.busOpId = busOpId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BusOpData getBusopdata() {
        return busOpData;
    }

    public void setBusopdata(BusOpData busopdata) {
        this.busOpData = busopdata;
    }
}

	


