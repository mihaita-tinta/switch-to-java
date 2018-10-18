package com.ing.switchtojava.domain;

public class CarBuilderImpl implements CarBuilder {

    private Car car;

    public CarBuilderImpl() {
        car = new Car();
    }

    @Override
    public Car build() {
        return car;
    }

    @Override
    public CarBuilder setId(final Long id) {
        car.setId(id);
        return this;
    }

    @Override
    public CarBuilder setNumber(final String number) {
        car.setNumber(number);
        return this;
    }

    @Override
    public CarBuilder setSeats(final int seats) {
        car.setSeats(seats);
        return this;
    }
}
