package com.ing.carpooling.domain;

public class Car {

    private Long id;
    private String number;
    private int seats;
    private Long idDriver;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId(){return idDriver;}

    public void setDriverId(Long idDriver){this.idDriver = idDriver;}

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
}
