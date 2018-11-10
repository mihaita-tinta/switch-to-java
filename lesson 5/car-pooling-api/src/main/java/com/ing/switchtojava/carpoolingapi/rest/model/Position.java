package com.ing.switchtojava.carpoolingapi.rest.model;

import java.math.BigDecimal;

public class Position {
    private BigDecimal lat;
    private BigDecimal lon;

    public Position() {
    }

    public Position(BigDecimal lat, BigDecimal lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLon() {
        return lon;
    }
}
