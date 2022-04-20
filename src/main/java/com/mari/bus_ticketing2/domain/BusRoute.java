package com.mari.bus_ticketing2.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "bus_routes_history")
public class BusRoute {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @Expose
    @Column(name = "date_of_journey")
    private String date;

    @Expose
    @Column(name = "remaining_tickets")
    private int remainingTickets;


    public BusRoute(Bus bus, String date, int remainingTickets) {
        this.bus = bus;
        this.date = date;
        this.remainingTickets = remainingTickets;
    }

    public BusRoute(){

    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    } 
}
