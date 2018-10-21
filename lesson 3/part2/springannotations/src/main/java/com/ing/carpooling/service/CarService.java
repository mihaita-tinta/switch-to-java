package com.ing.carpooling.service;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.repository.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Value("${application.car.default-seats}")
    int defaultSeats;

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        if (car.getSeats() == 0) {
            car.setSeats(defaultSeats);
        }

        return carRepository.save(car);
    }

}
