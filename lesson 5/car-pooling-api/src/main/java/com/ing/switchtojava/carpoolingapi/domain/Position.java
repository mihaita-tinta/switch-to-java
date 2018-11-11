package com.ing.switchtojava.carpoolingapi.domain;

import java.math.BigDecimal;

public class Position {
    private BigDecimal lat;

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

    private BigDecimal lon;

    public Position() {}
    public Position(BigDecimal lat, BigDecimal lon) {
        this.lat = lat;
        this.lon = lon;
    }


}
