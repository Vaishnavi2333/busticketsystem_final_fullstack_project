package com.hexaware.fastx_busticketsystem.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:Bus Operator Data Entity Class*/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "busop_data")
public class BusOpData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_op_id")
    private int busOpDataId;

    @OneToOne
    @MapsId   
    @JoinColumn(name = "bus_op_id")
    private BusOpLogin busOpLogin;

    private String name;
    private String companyName;
    private String licenceNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String contactNumber;
    private String address;

    @OneToMany(mappedBy = "busOpData", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Bus> buses = new HashSet<>();
    
    @OneToMany(mappedBy = "busOpData", cascade = CascadeType.ALL)
    private List<Route> routes;
    
   
    public BusOpData() {
    }

    public BusOpData(String name, String companyName, String licenceNumber, String gender,
                     LocalDate dateOfBirth, String email, String contactNumber, String address,
                     BusOpLogin busOpLogin) {
        this.name = name;
        this.companyName = companyName;
        this.licenceNumber = licenceNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
        this.busOpLogin = busOpLogin;
    }

   
    public int getBusOpId() {
        return busOpDataId;
    }

    public void setBusOpId(int busOpId) {
        this.busOpDataId = busOpDataId;
    }
    
    

    public int getBusOpDataId() {
		return busOpDataId;
	}

	public void setBusOpDataId(int busOpDataId) {
		this.busOpDataId = busOpDataId;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public BusOpLogin getBusOpLogin() {
        return busOpLogin;
    }

    public void setBusOpLogin(BusOpLogin busOpLogin) {
        this.busOpLogin = busOpLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Bus> getBuses() {
        return buses;
    }

    public void setBuses(Set<Bus> buses) {
        this.buses = buses;
    }

    public void addBus(Bus bus) {
        bus.setBusOpData(this);
        this.buses.add(bus);
    }
}