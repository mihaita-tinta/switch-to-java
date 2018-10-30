package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements CrudRepository<Driver, Long> {

    private static final Logger log = LoggerFactory.getLogger(DriverRepository.class);

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   first_name VARCHAR(16) NOT NULL, \n" +
            "   last_name VARCHAR(16) NOT NULL, \n" +
            ");";


    private RowMapper<Driver> mapper = new RowMapper<Driver>() {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) throws SQLException {
            Driver driver = new Driver();

            try {
                 driver.setId(resultSet.getLong("id"));
                 driver.setFirstName(resultSet.getString("first_name"));
                 driver.setLastName(resultSet.getString("last_name"));

                List<Car> cars = driver.getCars();
                if (cars==null) {
                    cars = new ArrayList<Car>();
                }
                Car car = new Car();
                car.setId(resultSet.getLong("car_id"));
                car.setSeats(resultSet.getInt("seats"));
                car.setNumber(resultSet.getString("number"));

                cars.add(car);

            } catch (SQLException e) {
                throw new IllegalArgumentException("Error:: " + e.getMessage());
            }
            return driver;
        }
    };

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DriverRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Driver save(Driver instance) {

        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public List<Driver> findAll() {
        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

        try {
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("select t1.*, t2.id as car_id, t2.seats, t2.number from Driver t1 LEFT JOIN Car t2 ON (t1.id = t2.driver_id) where t1.id = :id", params, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no location found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud

    }
}
