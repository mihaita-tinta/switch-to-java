package com.ing.carpooling.domain;

public class Car {

    private Long id;
    private String number;
    private int seats;
    private Long driverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }



    public Long getDriverId() { return driverId; }

    public void setDriverId(Long driverId) { this.driverId = driverId; }
}
