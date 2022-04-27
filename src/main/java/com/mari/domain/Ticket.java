package com.mari.domain;

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

import com.google.gson.annotations.Expose;

import org.hibernate.annotations.Type;

@Entity
@Table(name="tickets")
public class Ticket {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;

    @Expose
    @OneToOne
    @JoinColumn(name = "bus_route_id")
    private BusRoute busRoute;

    @Expose
    @Column(name = "fare")
    private String fare;

    @Expose
    @Column(name = "date")
    private String date;

    @Expose
    @Column(name = "is_blocked")
    private boolean isAvailable;

    @Expose
    @Column(name = "seat_no")
    private int seatNo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Expose
    @Column(name = "booked_at")
    private Date bookedAt;

    @Expose
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_status")
    private boolean paymentStatus; 

    public Ticket(UUID id, BusRoute busRoute, String date, int seatNo, Date bookedAt) {
        this.id = id;
        this.busRoute = busRoute;
        this.date = date;
        this.seatNo = seatNo;
        this.bookedAt = bookedAt;
    }

    public Ticket(BusRoute busRoute, String fare, String date, boolean isAvailable, int seatNo, User user, Date bookedAt, String orderId,
            boolean paymentStatus) {
        this.busRoute = busRoute;
        this.fare = fare;
        this.date=date;
        this.isAvailable = isAvailable;
        this.seatNo = seatNo;
        this.user = user;
        this.bookedAt = bookedAt;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public Ticket(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BusRoute getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket [bookedAt=" + bookedAt + ", date=" + date + ", fare=" + fare + ", id=" + id + ", isAvailable="
                + isAvailable + ", orderId=" + orderId + ", paymentStatus=" + paymentStatus + ", seatNo=" + seatNo
                + ", user=" + user + "]";
    }

    
}
