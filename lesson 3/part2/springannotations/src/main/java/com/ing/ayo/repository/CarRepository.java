package com.ing.ayo.repository;

import com.ing.ayo.domain.Car;

import java.util.Optional;

public class CarRepository {

    public Car save(Car car) {
        // FIXME save car and generate unique id
        return car;
    }

    public Optional<Car> findOne(Long id) {

        // FIXME find car by id
        return Optional.empty();
    }
}
