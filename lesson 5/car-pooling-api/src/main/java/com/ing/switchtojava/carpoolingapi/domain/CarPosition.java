package com.ing.switchtojava.carpoolingapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.MessageFormat;

@Entity
public class CarPosition {

    @Id
    @GeneratedValue
    private Long id;
    BigDecimal latitude;
    BigDecimal longitude;

    public CarPosition(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() { return longitude; }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
