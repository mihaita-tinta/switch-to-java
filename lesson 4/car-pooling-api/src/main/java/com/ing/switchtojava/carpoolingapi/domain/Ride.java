package com.ing.switchtojava.carpoolingapi.domain;


import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import com.ing.switchtojava.carpoolingapi.domain.Location;

@Entity
public class Ride {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Location from;
    @OneToOne
    private Location to;
    private ZonedDateTime when;

    @OneToOne
    private Car car;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
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

    public com.ing.switchtojava.carpoolingapi.domain.Car getCar() {
        return car;
    }

    public void setCar(com.ing.switchtojava.carpoolingapi.domain.Car car) {
        this.car = car;
    }

    public List<com.ing.switchtojava.carpoolingapi.domain.Passenger> getPassengers() { return passengers; }

    public void setPassengers(List<com.ing.switchtojava.carpoolingapi.domain.Passenger> passengers) { this.passengers = passengers; }

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

    public static enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELED
    }
}
