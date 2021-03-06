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

    @Enumerated(EnumType.STRING)
    private Status status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELED
    }
}
