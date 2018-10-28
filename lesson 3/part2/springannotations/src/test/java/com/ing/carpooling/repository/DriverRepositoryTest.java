package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

public class DriverRepositoryTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(DriverRepositoryTest.class.getName());

    CarRepository carRepository = context.getBean(CarRepository.class);
    DriverRepository repository = context.getBean(DriverRepository.class, carRepository);


    @Test
    public void testSaveToDatabase() {
        Car car = new Car();
        car.setNumber("B101asd");
        car.setSeats(5);
        car.setDriverId(1l);
        //Car saved = carRepository.save(car);
        System.out.println(car);

        Car car1 = new Car();
        car1.setNumber("BV221asd");
        car1.setSeats(6);
        car1.setDriverId(2l);

        Car car2 = new Car();
        car2.setNumber("TM100TIM");
        car2.setSeats(3);
        car2.setDriverId(2l);

        Driver driver = new Driver();
        driver.setLastName("Dobre");
        driver.setFirstName("Andrei");
        driver.setCars(Arrays.asList(car));
        System.out.println(driver);

        Driver driver1 = new Driver();
        driver1.setLastName("Vasilica");
        driver1.setFirstName("Ion");
        driver1.setCars(Arrays.asList(car1, car2));

        repository.save(driver);
        repository.save(driver1);

        //repository.findAll();
    }

    @Test
    public void testGetObject(){
        List<Driver> drivers = repository.findAll();
        for(Driver driver : drivers){
            System.out.println(driver);
        }
//        System.out.println("######");
//        List<Driver> dd = drivers.stream()
//                .filter(e->e.getId() == 3L)
//                .collect(Collectors.toList());
//        if (dd.size() != 1) {
//            throw new IllegalStateException();
//        }
//        System.out.println(dd.get(0));
    }

    @Test
    public void testSearchOneDriverByID(){
        System.out.println(repository.findOne(1L));

        System.out.println(repository.findOne(2L));

        System.out.println(repository.findOne(3L));
    }

    @Test
    public void deleteDriverByID(){
        repository.delete(1L);
//        List<Car> cars = carRepository.findCarsByDriverId(2L);
//        for(Car car : cars){
//            System.out.println(car);
//        }
    }
}
