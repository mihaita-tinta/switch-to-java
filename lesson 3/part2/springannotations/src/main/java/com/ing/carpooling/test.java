package com.ing.carpooling;

//import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.*;
import com.ing.carpooling.repository.LocationRepository;

import java.lang.reflect.Array;
import java.util.Arrays;

public class test {

    public static void main(String[] args){

        //LocationRepository repository = context.getBean(LocationRepository.class);

        Car car = new Car();
        car.setNumber("IL11OIS");
        car.setId(1l);
        car.setSeats(3);
        car.setDriverId(1l);
        //Car saved = repository.save(car);

        Driver driver =new Driver();
        driver.setId(1l);
        driver.setFirstName("adi");
        driver.setLastName("thewonder");
        driver.setCars(Arrays.asList(car));
    }
}
