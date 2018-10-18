package com.ing.switchtojava.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ing.switchtojava.serialization.CarDeserializer;

import javax.swing.text.html.Option;

public class Car {

    // TODO 3 implement Builder pattern

	private Long id;
	
	private String number;
	
	private int seats;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		LinkedHashMap<String, String> dict = new LinkedHashMap<String, String>();
		dict.put("\"id\"", "\"" + Optional.ofNullable(getId().toString()).orElse("") + "\"");
		dict.put("\"number\"", "\"" + Optional.ofNullable(getNumber()).orElse("") + "\"");
		dict.put("\"seats\"", "\"" + Optional.ofNullable(((Integer)getSeats()).toString()).orElse("") + "\"");

		return dict.toString();
	}

	public CarDeserializer deserializer() {
		return new CarDeserializer();
	}
}
