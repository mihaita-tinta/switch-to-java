package com.ing.switchtojava.domain;

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
//        System.out.println("+++++++ Am setat id");
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {

	    this.number = number;
//        System.out.println("+++++++ Am setat number");
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
//        System.out.println("+++++++ Am setat seats");
	}

	public static class Builder{
		private Car c = new Car();

		public Builder setSeats(int seats){
			c.seats =seats;
			return this;
		}
		public Builder setNumber(String number){
			c.number =number;
			return this;
		}
		public Builder setId(Long id){
			c.id =id;
			return this;
		}

		public Car build(){
		    return c;
		}

	}

    @Override
    public String toString() {
        return "Car<" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", seats=" + seats +
                '>';
    }
}
