package com.ing.ayo.domain;

import java.time.ZonedDateTime;
import java.util.List;

public class Ride {

    private Location from;
    private Location to;
    private ZonedDateTime when;
    private Car car;
    private Status status;
    private List<Passenger> passengers;

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAvailableSeats() {
        // FIXME what is the number of available seats for this ride?
        return 0;
    }

    public static enum Status {
        PENDING,
        IN_PROGRESS,
        REJECTED,
        CANCELED
    }
}
