package com.ing.switchtojava.serialization;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.domain.Car;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class CarDeserializer implements ObjectDeserializer{

	@Override
	public Car deserialize(String string) throws DeserializationException {
		// TODO 5 Auto-generated method stub

		if (string.length() < 2)
			return null;

		Car car = new Car();
		String[] pairs = string.substring(1, string.length() - 1).split(",");
		for (String pair : pairs) {
			String[] keyValue = Arrays.stream(pair.split(":")).map(p -> p.trim()).toArray(String[]::new);
			switch(keyValue[0]) {
				case "\"id\"":
					car.setId(Long.parseLong(keyValue[1].substring(1, keyValue[1].length() - 1)));
					break;
				case "\"number\"":
					car.setNumber(keyValue[1].substring(1, keyValue[1].length() - 1));
					break;
				case "\"seats\"":
					car.setSeats(Integer.parseInt(keyValue[1].substring(1, keyValue[1].length() - 1)));
					break;
				default:
					System.out.println("None of expected properties!");
					break;
			}
		}

		return car;
	}

	@Override
	public Car deserializeWithLib(String string) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(string, Car.class);
	}

}
