package com.ing.switchtojava.carpoolingapi.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ing.switchtojava.carpoolingapi.domain.Car;

import javax.persistence.Entity;
import java.util.List;

public class CarRequest {
    List<Car> cars;

    //public List<Car> setCars(){}
}
