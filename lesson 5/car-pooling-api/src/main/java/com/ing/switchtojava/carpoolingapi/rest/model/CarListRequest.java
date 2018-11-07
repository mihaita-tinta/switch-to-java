package com.ing.switchtojava.carpoolingapi.rest.model;

import com.ing.switchtojava.carpoolingapi.domain.Car;

import java.util.List;

public class CarListRequest {
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
