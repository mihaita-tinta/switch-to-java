package com.ing.switchtojava;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.CarBuilder;

/**
 * Hello world!
 *
 */
public class App  {
	
    public static void main( String[] args ) {

        System.out.println( "Hello World!" );

        Car car = new CarBuilder().setId(Long.parseLong("1"))
                .setNumber("B108HSH")
                .setSeats(4)
                .build();
        System.out.println(car);
    }
}
