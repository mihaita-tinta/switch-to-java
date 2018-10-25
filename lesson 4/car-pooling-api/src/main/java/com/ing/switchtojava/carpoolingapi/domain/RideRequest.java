package com.ing.switchtojava.carpoolingapi.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class RideRequest {

    @Id
    @GeneratedValue
    private Long id;
    private Passenger passenger;
    private Ride ride;
    private Status status;

    public static enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELED
    }
}
