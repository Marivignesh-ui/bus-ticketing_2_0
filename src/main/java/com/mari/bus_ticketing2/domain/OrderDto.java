package com.mari.bus_ticketing2.domain;

public class OrderDto {
    private long amount;
    private String receipt;

    public OrderDto(long amount, String receipt) {
        this.amount = amount;
        this.receipt = receipt;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "OrderDto [amount=" + amount + ", receipt=" + receipt + "]";
    }

    
    
    
}
