package com.ing.switchtojava.carpoolingapi.service;

import java.math.BigDecimal;

public class RiderCoordonates {
    private BigDecimal latitude;
    private  BigDecimal longitude;

    public RiderCoordonates(BigDecimal lon, BigDecimal lat) {
        latitude = lat;
        longitude = lon;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
