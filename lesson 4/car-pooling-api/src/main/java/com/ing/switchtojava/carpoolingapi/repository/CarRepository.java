package com.ing.switchtojava.carpoolingapi.repository;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

}
