package com.ing.switchtojava;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.serialization.CarDeserializer;
import com.ing.switchtojava.serialization.ObjectSerializer;
import com.ing.switchtojava.serialization.ObjectSerializerImpl;
import com.ing.switchtojava.serialization.TDeserializer;

/**
 * Hello world!
 *
 */
public class App  {
	
    public static void main( String[] args ) {

        System.out.println( "Hello World!" );
        String json = "{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}";
        Car c1 = Car.build().setId(1L).setNumber("B357WEB").setSeats(5);

        CarDeserializer cd          = new CarDeserializer();
        TDeserializer d             = new TDeserializer();
        ObjectSerializerImpl os     = new ObjectSerializerImpl();

        try {
            String jsonCar = os.serialize(c1);
            System.out.println("My Serialised Car is: "  + jsonCar);

            Car objectCar = cd.deserialize(json);
            //Car objectCar = d.deserialize(json, Car.class);
            System.out.println("My Deserialised Car is: "  + objectCar.getNumber());

        } catch (Exception e) {
            System.out.println("Error:: " + e.getMessage());
        }




    }
}
