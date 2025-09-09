package com.hexaware.fastx_busticketsystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;




/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:07-Aug-2025
Description:Bus Entity Class*/



@Entity
@Table(name="bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int busId;

    private String busNumber;
    private String busName;
    private String busType;
    private int capacity;
    private String status;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="route_id")
    private Route route;


    @ManyToOne
    @JoinColumn(name = "operator_id")
    @JsonManagedReference
    private BusOpData busOpData;
    
    @OneToMany(mappedBy = "bus",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BusAmenity> amenities = new ArrayList<>();
    
    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    @JsonManagedReference 
    private List<Trip> trips;

    public Bus() {
    	
    	
     
    }



	public Bus(int busId, String busNumber, String busName, String busType, int capacity, String status, BusOpData busOpData, List<BusAmenity> amenities, List<Trip> trips) {
		super();
		this.busId = busId;
		this.busNumber = busNumber;
		this.busName = busName;
		this.busType = busType;
		this.capacity = capacity;
		this.status = status;
		
		this.busOpData = busOpData;
		this.amenities = amenities;
		this.trips = trips;
	}




	public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BusOpData getBusOpData() {
        return busOpData;
    }

    public void setBusOpData(BusOpData busOpData) {
        this.busOpData = busOpData;
    }
    




	public List<BusAmenity> getAmenities() {
		return amenities;
	}


	public void setAmenities(List<BusAmenity> amenities) {
		this.amenities = amenities;
	}


    
    public void addBusAmenity(BusAmenity busAmenity) {
        amenities.add(busAmenity);
        busAmenity.setBus(this); 
    }
    
    public void removeBusAmenity(BusAmenity busAmenity) {
        amenities.remove(busAmenity);
        busAmenity.setBus(null); 
    }
    
    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
    
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}

