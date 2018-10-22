package com.ing.ayo.repository;

import com.ing.ayo.domain.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CarRepository {

    public Car save(Car car) {
        // FIXME save car and generate unique id

        Logger log = LoggerFactory.getLogger(CarRepository.class.getName());
        String url = "jdbc:h2:tcp://localhost:9092/testdb";

        try (Connection con = DriverManager.getConnection(url, "sa", "")) {
            PreparedStatement st = con.prepareStatement("INSERT INTO CAR VALUES (?, ?, ?);");
            st.setLong(1, car.getId());
            st.setString(2, car.getNumber());
            st.setInt(3, car.getSeats());
            ResultSet rs = st.executeQuery();
        }

        return car;
    }

    public Optional<Car> findOne(Long id) {

        // FIXME find car by id
        return Optional.empty();
    }
}
