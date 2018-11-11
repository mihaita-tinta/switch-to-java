package com.ing.switchtojava.carpoolingapi.service;

import java.math.BigDecimal;

public class Position {
    private BigDecimal lat;
    private BigDecimal lon;


    public Position(){}
    public Position(BigDecimal lat, BigDecimal lon){
        this.lat = lat;
        this.lon = lon;
    }
    public BigDecimal getLat() {
        return lat;
    }
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
    public BigDecimal getLon() {
        return lon;
    }
    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Position{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
