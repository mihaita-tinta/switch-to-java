package com.ing.switchtojava.domain;

import java.util.List;

public class Driver {

	private String firstName;
	private String lastName;
	
	private List<Car> cars;
	
	// TODO 2 generate getters/setters

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
}
