package com.ing.switchtojava.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class Driver {

	private String firstName;
	private String lastName;
	
	private List<Car> cars;

	// TODO 2 generate getters/setters

	public String getFirstName() { return this.firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return this.lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public List<Car> getCars() { return this.cars; }

	public void setCars(List<Car> cars) { this.cars = cars; }

    @Override
    public String toString() {
        LinkedHashMap<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("\"firstName\"", "\"" + Optional.ofNullable(getFirstName()).orElse("")+ "\"");
        dict.put("\"lastName\"", "\"" + Optional.ofNullable(getLastName()).orElse("") + "\"");
        dict.put("\"cars\"", Optional.ofNullable(getCars().toString()).orElse(""));

        return dict.toString();
    }
}
