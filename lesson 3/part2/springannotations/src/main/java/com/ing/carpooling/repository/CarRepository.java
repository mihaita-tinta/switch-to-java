package com.ing.carpooling.repository;

import com.ing.carpooling.domain.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarRepository implements CrudRepository<Car, Long> {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   number VARCHAR(7) NOT NULL, \n" +
            "   seats INT NOT NULL, \n" +
            "   driver_id INT NOT NULL, \n" +
            ");";

    private final RowMapper<Car> mapper = new RowMapper<Car>() {
        @Override
        public Car mapRow(ResultSet resultSet, int i) {
            Car car = new Car();
            try {
                car.setId(resultSet.getLong("id"));
                car.setNumber(resultSet.getString("number"));
                car.setSeats(resultSet.getInt("seats"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to car", e);
            }
            return car;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CarRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Car save(Car instance) {
        // TODO 0 implement Car crud
        return null;
    }

    @Override
    public List<Car> findAll() {
        // TODO 0  implement Car crud
        return null;
    }

    @Override
    public Optional<Car> findOne(Long id) {
        // TODO 0  implement Car crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Car crud
    }
}
