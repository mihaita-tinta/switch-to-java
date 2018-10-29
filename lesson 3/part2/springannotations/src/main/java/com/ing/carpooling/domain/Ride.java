package com.ing.carpooling.domain;

import java.time.ZonedDateTime;
import java.util.List;

public class Ride {

    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", when=" + when +
                ", car=" + car +
                ", status=" + status +
                ", passengers=" + passengers +
                '}';
    }

    public static enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELED
    }
}
