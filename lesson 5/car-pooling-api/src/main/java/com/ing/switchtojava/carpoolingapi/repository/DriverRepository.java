package com.ing.switchtojava.carpoolingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.switchtojava.carpoolingapi.domain.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
