package com.ing.switchtojava.domain;

public class Car {

    // TODO 3 implement Builder pattern DONE

	public Car(Long id, String number, int seats) {
		this.id = id;
		this.number = number;
		this.seats = seats;
	}

	public  Car(){
		this.id = Long.parseLong("0");
		this.number = null;
		this.seats = 0;
	}

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
        return id + " " + number + " " + seats;
    }
}
