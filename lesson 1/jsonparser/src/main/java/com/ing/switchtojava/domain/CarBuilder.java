package com.ing.switchtojava.domain;

interface CarBuilder {

    Car build();

    CarBuilder setId(final Long color);

    CarBuilder setNumber(final String number);

    CarBuilder setSeats(final int seats);
}
