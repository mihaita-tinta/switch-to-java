package com.ing.switchtojava.carpoolingapi.rest.model;

import com.ing.switchtojava.carpoolingapi.domain.Car;

import java.util.List;

public class CarListRequest {
    private List<Car> cars;

    public List<Car> getCarList() {
        return cars;
    }

    public void setCarList(List<Car> carList) {
        this.cars = carList;
    }
}

