package com.ing.switchtojava.serialization;

import com.ing.switchtojava.App;
import com.ing.switchtojava.domain.Car;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class CarDeserializer implements ObjectDeserializer{

	@Override
	public Car deserialize(String string) throws DeserializationException {
		// TODO 5 Auto-generated method stub
		//"{\"id\":\"101\",\"number\":\"B-01-ERU\",\"seats\":\"5\"}"

		string = string.substring(1,string.length()-1).replaceAll("\"","");
		string = string.replaceAll(":|,"," ");
		String[] arr = string.split(" ");

		Car car = new Car();
		Class cls = car.getClass();
		Method[] allMethods = cls.getDeclaredMethods();

		for (int i=0; i<allMethods.length;i++) {
			String mname = allMethods[i].getName();

			if (!mname.startsWith("set")) {
				continue;
			}
			Type[] pType = allMethods[i].getGenericParameterTypes();
//            System.out.println("");
//            System.out.println("Numele metodei gasite: "+mname);
//            System.out.println("Tipul argumentului metodei: "+pType[0]);
//            System.out.println("Valoare salvata pentru metoda: "+value);

			String value="";
			for(int j=0;j<arr.length;j=j+2){
				if(new String("set"+arr[j].substring(0,1).toUpperCase()+ arr[j].substring(1)).equals(mname)){
					value = arr[j+1];
				}
			}

			try {

				Object ob;
				ob = App.toObject((Class) pType[0], value);
				allMethods[i].invoke(car,ob);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return car;
	}

}
