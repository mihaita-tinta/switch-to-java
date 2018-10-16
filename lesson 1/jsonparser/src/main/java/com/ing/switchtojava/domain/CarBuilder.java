package com.ing.switchtojava.domain;

public class CarBuilder {
    //for builder pattern
    private Car car;

    public CarBuilder(){
        car = new Car();
    }


    public CarBuilder setId(Long id) {
        this.car.setId(id);
        return this;
    }


    public CarBuilder setNumber(String number) {
        this.car.setNumber(number);
        return this;
    }


    public CarBuilder setSeats(int seats) {
        this.car.setSeats(seats);
        return this;
    }

    public Car build(){
        return car;
    }
}
