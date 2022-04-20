package com.mari.bus_ticketing2.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "bus_master")
public class Bus {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Expose
    @Column(name="start_terminal")
    private String startTerminal;

    @Expose
    @Column(name = "end_terminal")
    private String endTerminal;

    @Expose
    @Column(name = "registration_no")
    private String registrationNumber;

    @Expose
    @Column(name = "travels_name")
    private String travelsName;

    @Expose
    @Column(name = "bus_type")
    private String busType;

    @Expose
    @Column(name = "total_seats")
    private int totalSeats;

    @Expose
    @Column(name = "journey_type")
    private String journeyType;

    @Expose
    @OneToMany(mappedBy = "bus")
    private List<BusRoute> busRoutes=new ArrayList<>();

    public Bus(String startTerminal, String endTerminal, String registrationNumber, String travelsName, String busType,
            int totalSeats, String journeyType) {
        this.startTerminal = startTerminal;
        this.endTerminal = endTerminal;
        this.registrationNumber = registrationNumber;
        this.travelsName = travelsName;
        this.busType = busType;
        this.totalSeats = totalSeats;
        this.journeyType = journeyType;
    }

    public Bus(){    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(String startTerminal) {
        this.startTerminal = startTerminal;
    }

    public String getEndTerminal() {
        return endTerminal;
    }

    public void setEndTerminal(String endTerminal) {
        this.endTerminal = endTerminal;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getTravelsName() {
        return travelsName;
    }

    public void setTravelsName(String travelsName) {
        this.travelsName = travelsName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public List<BusRoute> getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(List<BusRoute> busRoutes) {
        this.busRoutes = busRoutes;
    }

    @Override
    public String toString() {
        return "Bus [busType=" + busType + ", endTerminal=" + endTerminal + ", id=" + id + ", journeyType="
                + journeyType + ", registrationNumber=" + registrationNumber + ", startTerminal=" + startTerminal
                + ", totalSeats=" + totalSeats + ", travelsName=" + travelsName +", busRoutes="+busRoutes+ "]";
    }
    
}
