package com.ing.switchtojava.carpoolingapi.rest;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars/")
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    private final CarService carService;
    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping
    public Iterable<Car> findAll() {
        return carService.findAll();
    }

//    @GetMapping("{id}")
//    public Car findById(@PathVariable Long id){
//        return repository.findById(id)
//                .orElseThrow(() -> new CarNotFoundException());
//    }

//    @PutMapping
//    public Car save(@Valid @RequestBody Car car){
//        return  repository.save(car);
//    }


//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleLocationNotFound(LocationNotFoundException e) {
//        log.warn("handleCarNotFound - " + e);
//    }
}
