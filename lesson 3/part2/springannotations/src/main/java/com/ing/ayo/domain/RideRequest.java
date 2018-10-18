package com.ing.ayo.domain;

public class RideRequest {

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
