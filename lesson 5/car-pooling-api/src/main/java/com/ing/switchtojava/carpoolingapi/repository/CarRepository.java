package com.ing.switchtojava.carpoolingapi.repository;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAll(Specification<Car> specification);
}
