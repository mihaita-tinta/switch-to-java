package com.ing.switchtojava.domain;

public class Car {

    // TODO 3 implement Builder pattern

	private Long id;
	private String number;
	private int seats;

	public Car(){}

	private Car(BuilderCar builder){
		this.id = builder.id;
		this.number = builder.number;
		this.seats = builder.seats;
	}

	public Long getId() {
		return id;
	}
	//public void setId(Long id) {
	//	this.id = id;
	//}
	public String getNumber() {
		return number;
	}
//	public void setNumber(String number) {
//		this.number = number;
//	}
	public int getSeats() {
		return seats;
	}
	//public void setSeats(int seats) {
	//	this.seats = seats;
	//}

	public static class BuilderCar{

		private Long id;
		private String number;
		private int seats;

	//	public BuilderCar(Long id, String number, int seats){
	//		this.id = id;
	//		this.number = number;
	//		this.seats = seats;
	//	}

		public BuilderCar id(Long id){
			this.id = id;
			return this;
		}

		public BuilderCar number(String number){
			this.number = number;
			return this;
		}

		public BuilderCar seats(int seats){
			this.seats = seats;
			return this;
		}

		public Car build(){
			return new Car(BuilderCar.this);
		}

	}

}

