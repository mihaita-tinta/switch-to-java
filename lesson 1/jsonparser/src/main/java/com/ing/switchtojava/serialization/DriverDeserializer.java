package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;
import com.ing.switchtojava.domain.Driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriverDeserializer implements ObjectDeserializer{

	@Override
	public Driver deserialize(String string) throws DeserializationException {
		// TODO 6 Auto-generated method stub

		if (string.length() < 2)
			return null;

		Driver driver = new Driver();
		String[] pairs = string.substring(1, string.length() - 1).split(",");
		boolean insideObject = false;
		boolean firstObjElem = false;
		ArrayList<StringBuilder> carsObj = new ArrayList<StringBuilder>();
		for (String pair : pairs) {
			String[] keyValue =  Arrays.stream(pair.split(":")).map(p -> p.trim()).toArray(String[]::new);
			switch(keyValue[0]) {
				case "\"firstName\"":
					driver.setFirstName(keyValue[1].substring(1, keyValue[1].length() - 1));
					break;
				case "\"lastName\"":
					driver.setLastName(keyValue[1].substring(1, keyValue[1].length() - 1));
					break;
				case "\"cars\"":
				    String remainingStr = String.join(":", Arrays.copyOfRange(keyValue, 1, keyValue.length)).substring(1);
					if (remainingStr.startsWith("{")) {
						insideObject = true;
						carsObj.add(new StringBuilder(remainingStr));
					}
					if (remainingStr.endsWith("}"))
					    insideObject = false;
					break;
				default:
					if (insideObject) {
					    if (!firstObjElem)
                            carsObj.get(carsObj.size() - 1).append(",");

						carsObj.get(carsObj.size() - 1).append(pair);
						firstObjElem = false;
						if (pair.endsWith("}]")) {
                            insideObject = false;
                            carsObj.get(carsObj.size() - 1).deleteCharAt(carsObj.get(carsObj.size() - 1).length() - 1);
                        }

					}
					else {
						System.out.println("None of expected properties!");
					}
					break;
			}
			if (insideObject && pair.endsWith("}")) {
                carsObj.add(new StringBuilder(""));
                firstObjElem = true;
            }
		}

		Car c;
		List<Car> cars = new ArrayList<Car>();
		for (StringBuilder carObj : carsObj) {
            c = new Car();
            cars.add(c.deserializer().deserialize(carObj.toString()));
        }

        driver.setCars(cars);

		return driver;
	}

    @Override
    public Driver deserializeWithLib(String string) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(string, Driver.class);
    }

}
