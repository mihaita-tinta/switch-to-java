package com.ing.ayo.domain;

import java.util.List;

public class Driver extends Passenger {

    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
