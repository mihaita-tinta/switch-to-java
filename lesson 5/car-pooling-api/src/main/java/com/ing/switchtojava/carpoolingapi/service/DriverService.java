package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.Driver;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.rest.DriverNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DriverService {
    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Driver save(Driver driver) {
        return repository.save(driver);
    }

    public Car saveCar(Long id, Car car) throws DriverNotFoundException {
        Driver driver   = repository.findById(id).orElseThrow(() -> new DriverNotFoundException());

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Driver");
        EntityManager entitymanager = factory.createEntityManager();
        driver.addCar(car);
        entitymanager.merge(driver);
        entitymanager.close();
        return driver.getCars().get(driver.getCars().size()-1);
    }

    public List<Driver> findAll() {
        return repository.findAll();
    }

    public Optional<Driver> findById(Long id) {
        return repository.findById(id);
    }

    public List<Car> findCarsById(Long id) throws DriverNotFoundException {
        List <Car> cars = new ArrayList<>();
        Driver driver = repository.findById(id).orElseThrow(() -> new DriverNotFoundException());
        if (driver.getCars().size() > 0) {
            cars = driver.getCars();
        }
        return cars;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}