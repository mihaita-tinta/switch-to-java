package com.ing.switchtojava.domain;

public class CarBuilder {

    private Long id;
    private String number;
    private int seats;

    public CarBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CarBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public CarBuilder setSeats(int seats) {
        this.seats = seats;
        return this;
    }

    public Car build() {
        Car car = new Car();
        car.setId(id);
        car.setNumber(number);
        car.setSeats(seats);
        return car;
    }

}
