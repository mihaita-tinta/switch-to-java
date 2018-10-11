package com.ing.switchtojava.domain;

import java.util.List;

public class Driver {

	private String firstName;
	private String lastName;
	
	private List<Car> cars;
	
	// TODO 2 generate getters/setters

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public void setCars(List<Car> cars){
		this.cars = cars;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public String getLastName(){
		return  this.lastName;
	}

	public List<Car> getCars(){
		return this.cars;
	}

    @Override
    public String toString() {
        return "Driver<" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cars=" + cars +
                '>';
    }
}




