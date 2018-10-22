package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DriverRepository implements CrudRepository<Driver, Long> {

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstname VARCHAR(50) NOT NULL, \n" +
            "   lastname VARCHAR(50) NOT NULL, \n" +
            ");";

    private final ResultSetExtractor<Driver> resultSetExtractor = new ResultSetExtractor<Driver>() {
        @Override
        public Driver extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Driver driver = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                if (driver == null) {
                    driver = new Driver();
                    driver.setId(id);
                    driver.setFirstName(resultSet.getString("firstname"));
                    driver.setLastName(resultSet.getString("lastname"));
                } else if (driver.getId().equals(id)) {
                    List<Car> carsList = driver.getCars();
                    if (carsList == null) {
                        carsList = new ArrayList<>();
                        driver.setCars(carsList);
                    }

                    Car car = new Car();
                    car.setId(resultSet.getLong("car_id"));
                    car.setNumber(resultSet.getString("number"));
                    car.setSeats(resultSet.getInt("seats"));
                    carsList.add(car);
                }
            }

            return driver;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
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
        // TODO 0 implement Driver crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.query("select DRIVER.*, CAR.id as car_id, CAR.number as number, CAR.seats as seats from DRIVER inner join CAR on CAR.driver_id = DRIVER.id where id = :id", parameters, resultSetExtractor));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no driver found for id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud

    }
}
