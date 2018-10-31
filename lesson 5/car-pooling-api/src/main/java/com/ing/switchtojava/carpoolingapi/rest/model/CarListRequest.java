package com.ing.switchtojava.carpoolingapi.rest.model;

import com.ing.switchtojava.carpoolingapi.domain.Car;

import java.util.List;

public class CarListRequest {
    private List<Car> carList;

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}

