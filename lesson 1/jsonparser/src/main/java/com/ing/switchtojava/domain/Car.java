package com.ing.switchtojava.domain;

public class Car {

	private Long id;
	
	private String number;
	
	private int seats;

	public Long getId() {
		return id;
	}

	public Car setId(Long id) {
		this.id = id;
		return this;
	}

	public String getNumber() {
		return number;
	}

	public Car setNumber(String number) {
		this.number = number;
		return this;
	}

	public int getSeats() {
		return seats;
	}

	public Car setSeats(int seats) {
		this.seats = seats;
		return this;
	}

	public static Car build() {
		return new Car();
	}
}
