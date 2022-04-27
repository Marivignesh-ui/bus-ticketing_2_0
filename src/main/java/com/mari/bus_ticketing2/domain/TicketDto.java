package com.mari.bus_ticketing2.domain;

public class TicketDto {
    private String seatId;


    public TicketDto(){
    }
    public TicketDto(String seatId){
        this.seatId=seatId;
    }

    public String getSeatId(){
        return this.seatId;
    }

    public void setSeatId(String seatId){
        this.seatId=seatId;
    }
}
