package com.ing.carpooling.domain;

public class RideRequest {

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
