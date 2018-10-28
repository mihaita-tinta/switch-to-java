package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CarRepositoryTest extends RepositoryIntegrationTest{
    private static final Logger log = LoggerFactory.getLogger(CarRepositoryTest.class.getName());

    CarRepository carRepository = context.getBean(CarRepository.class);

    @Test
    public void testSaveToDatabase() {
        Car car = new Car();
        car.setNumber("B101asd");
        car.setSeats(5);
        car.setDriverId(2l);
        Car saved = carRepository.save(car);
        System.out.println(car);
        assertNotNull(saved.getId());

        Car car1 = new Car();
        car1.setNumber("BV221asd");
        car1.setSeats(6);
        car1.setDriverId(2l);
        Car carr = carRepository.save(car1);
        System.out.println(carr);
        assertNotNull(carr.getId());
    }
    @Test
    public void testGetObject(){
        List<Car> cars = carRepository.findAll();
        for(Car car : cars){
            System.out.println(car);
        }
    }
    @Test
    public void testDeleteCar(){
        List<Car> cars = carRepository.findAll();
        for(Car car : cars){
            carRepository.delete(car.getId());
        }

    }

    @Test
    public void testFindById(){
        System.out.println(carRepository.findOne(1L));
        System.out.println(carRepository.findOne(34L));
    }





}
