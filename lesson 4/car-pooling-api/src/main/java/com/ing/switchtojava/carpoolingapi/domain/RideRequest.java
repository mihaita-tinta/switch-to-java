package com.ing.switchtojava.carpoolingapi.domain;

import javax.persistence.*;

@Entity
public class RideRequest {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Passenger passenger;
    @ManyToOne
    private Ride ride;
    private Status status;

    public static enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELED
    }
}
