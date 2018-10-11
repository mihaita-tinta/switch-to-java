package com.ing.switchtojava;

import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;
import com.ing.switchtojava.serialization.*;

import java.lang.reflect.*;
import java.util.*;


/**
 * Hello world!
 *
 */
public class App  {

    public static Object toObject( Class clazz, String value ) {
        if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz ) return Byte.parseByte( value );
        if( Short.class == clazz ) return Short.parseShort( value );
        if( Integer.class == clazz ) return Integer.parseInt( value );
        if( Long.class == clazz ) return Long.parseLong( value );
        if( Float.class == clazz ) return Float.parseFloat( value );
        if( Double.class == clazz ) return Double.parseDouble( value );
        if( int.class == clazz ) return Integer.parseInt( value );
        return value;
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        Car c = new Car.Builder()
                .setId(101L)
                .setNumber("B-01-ERU")
                .setSeats(new Integer(5))
                .build();

        Car c2 = new Car.Builder()
                .setId(19L)
                .setNumber("IL-01-ERU")
                .setSeats(new Integer(3))
                .build();

        System.out.println(c.getId());
        System.out.println(c.getNumber());
        System.out.println(c.getSeats());
        System.out.println("#################");

        String g = "{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}";
        System.out.println(g);


//        ObjectSerializer serializer = new ObjectSerializerImpl();
//        String json = "";
//        try {
//            json = serializer.serialize(c);
//        } catch (SerializationException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(json);

//        CarDeserializer deserializer = new CarDeserializer();
//
//        try {
//            Car car = deserializer.deserialize("{\"id\":\"100\",\"number\":\"B-01-ERU\",\"seats\":\"2\"}");
//            System.out.println(car);
//        } catch (DeserializationException e) {
//            e.printStackTrace();
//        }




        Driver driv = new Driver();
        driv.setFirstName("Ion");
        driv.setLastName("Vasile");
        List<Car> l = new ArrayList<Car>();
        l.add(c);
        l.add(c2);

        driv.setCars(l);


        System.out.println(driv.getFirstName());
        System.out.println(driv.getLastName());
        System.out.println(driv.getCars());
        System.out.println(driv);
        System.out.println("################# EXIT");
//
        ObjectSerializer serializer = new ObjectSerializerImpl();
        String json = "";
        try {
            json = serializer.serialize(driv);
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        System.out.println(json);


//        System.out.println("################# Deserializer");
//        DriverDeserializer deserializer = new DriverDeserializer();
//        String tt = "{\"firstname\":\"Ion\",\"lastname\":\"Vasile\",\"cars\":\"[Car<id=101, number='B-01-ERU', seats=5>, Car<id=19, number='IL-01-ERU', seats=3>]\"}";
//        try {
//            Driver drive = deserializer.deserialize("{\"firstname\":\"Ion\",\"lastname\":\"Vasile\",\"cars\":\"[Car<id=101, number='B-01-ERU', seats=5>, Car<id=19, number='IL-01-ERU', seats=3>]\"}");
//            System.out.println(drive);
//        } catch (DeserializationException e) {
//            e.printStackTrace();
//        }



//        String strr ="{\"id\":\"1091\",\"number\":\"IL-01-ERU\",\"seats\":\"3\"}";
//
//        strr = strr.substring(1,strr.length()-1).replaceAll("\"","");
//        strr = strr.replaceAll(":|,"," ");
//        String[] arr = strr.split(" ");
//
//        Car d = new Car();
//        Class cls = d.getClass();
//        Method[] allMethods = cls.getDeclaredMethods();
//
//        for (int i=0; i<allMethods.length;i++) {
//            String mname = allMethods[i].getName();
//
//            if (!mname.startsWith("set")) {
//                continue;
//            }
//            Type[] pType = allMethods[i].getGenericParameterTypes();
////            System.out.println("");
////            System.out.println("Numele metodei gasite: "+mname);
////            System.out.println("Tipul argumentului metodei: "+pType[0]);
////            System.out.println("Valoare salvata pentru metoda: "+value);
//
//            String value="";
//            for(int j=0;j<arr.length;j=j+2){
//                if(new String("set"+arr[j].substring(0,1).toUpperCase()+ arr[j].substring(1)).equals(mname)){
//                    value = arr[j+1];
//                }
//            }
//
//            try {
//
//                Object ob;
//                ob = App.toObject((Class) pType[0], value);
//                allMethods[i].invoke(d,ob);
//
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }


//        System.out.println("##########");
//        System.out.println("ID:  "+d.getId());
//        System.out.println("Numar:  "+d.getNumber());
//        System.out.println("Seats:  "+d.getSeats());
//
//        System.out.println(d);





//        Method[] allMethods = Car.Builder.class.getDeclaredMethods();
//
//        for (int i =0;i<allMethods.length;i++){
//            String mname = allMethods[i].getName();
//            System.out.println(mname);
//
//        }


        }



    }
