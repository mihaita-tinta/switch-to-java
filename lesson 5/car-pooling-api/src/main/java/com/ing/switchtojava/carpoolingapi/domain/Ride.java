package com.ing.switchtojava.carpoolingapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.ing.switchtojava.carpoolingapi.domain.View.View;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@JsonView(View.Public.class)
public class Ride {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Location from;
    @OneToOne
    private Location to;

    @DateTimeFormat
    private ZonedDateTime when;

    @OneToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<Passenger> passengers;

    @OneToMany(mappedBy = "ride" )
    @JsonBackReference
    private List<RideRequest> requests;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RideRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<RideRequest> requests) {
        this.requests = requests;
    }

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

    public static enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELED
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
                '}';
    }
}
