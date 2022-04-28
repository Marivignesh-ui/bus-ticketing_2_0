package com.mari.bus_ticketing2.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



import org.hibernate.annotations.Type;

@Entity
@Table(name = "blocked_tickets")
public class BlockedTicket {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne()
    @JoinColumn(name = "bus_route_id")
    private BusRoute busRoute;

    
    @Column(name = "date")
    private String date;

    
    @Column(name = "is_blocked")
    private boolean isBlocked;

    
    @Column(name = "seat_id")
    private String seatId;

    
    @Column(name = "seat_no")
    private int seatNumber;

    
    @Column(name = "booked_at")
    private Date bookedAt;

    public BlockedTicket(BusRoute busRoute, String date, boolean isBlocked, String seatId, int seatNumber, Date bookedAt) {
        this.busRoute = busRoute;
        this.date=date;
        this.isBlocked = isBlocked;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.bookedAt = bookedAt;
    }

    public BlockedTicket() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BusRoute getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    
}
