package com.ing.switchtojava.carpoolingapi.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.switchtojava.carpoolingapi.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAll(Specification<Car> specification);
}
