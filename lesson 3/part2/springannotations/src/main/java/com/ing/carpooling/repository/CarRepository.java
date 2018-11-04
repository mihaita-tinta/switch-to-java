package com.ing.carpooling.repository;

import com.ing.carpooling.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarRepository implements CrudRepository<Car, Long> {

    private static final Logger log = LoggerFactory.getLogger(CarRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   driver_id INT NOT NULL, \n" +
            "   number VARCHAR(16) NOT NULL, \n" +
            "   seats INT(1) NOT NULL \n" +
            ");";

    private final RowMapper<Car> mapper = new RowMapper<Car>() {
        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Car car = new Car();
            try {
                car.setId(resultSet.getLong("id"));
                car.setNumber(resultSet.getString("number"));
                car.setSeats(resultSet.getInt("seats"));
            } catch (SQLException e) {
                throw new IllegalArgumentException("Could not map", e);
            }
            return car;
        }
    };

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Car save(Car instance) {
        if (instance.getId()==null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("number", instance.getNumber())
                    .addValue("seats", instance.getSeats());
            namedParameterJdbcTemplate.update("insert into Car (number, seats) values (:number, :seats)", params, holder);
            instance.setId(holder.getKey().longValue());
        }
        return instance;
    }

    public Car save(Car instance, Long driverId) {
        if (instance.getId()==null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = new MapSqlParameterSource()
                                            .addValue("driver_id", driverId)
                                            .addValue("number", instance.getNumber())
                                            .addValue("seats", instance.getSeats());
            namedParameterJdbcTemplate.update("insert into Car (driver_id, number, seats) values (:driver_id, :number, :seats)", params, holder);
            instance.setId(holder.getKey().longValue());
        }
        return instance;
    }

    @Override
    public List<Car> findAll() {
        log.info("findAll");
        return namedParameterJdbcTemplate.query("select * from Car", mapper);
    }

    public List<Car> findAllByDriver(Long driverId) {
        log.info("Find all driver cars");
        SqlParameterSource params = new MapSqlParameterSource().addValue(":driver_id", driverId);
        return namedParameterJdbcTemplate.query("select * from Car where driver_id = :driver_id", params, mapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

        try {
            return Optional.of(
            namedParameterJdbcTemplate.queryForObject("select * from Car where id = :id", params, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no location found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            namedParameterJdbcTemplate.update("delete from Car where id = :id", params);
        } catch (DataAccessException e) {
            log.info("Error::" + e.getMessage());
        }
    }

    public void deleteByDriver(Long driverId) throws DataAccessException {

        SqlParameterSource params = new MapSqlParameterSource().addValue("driver_id", driverId);
        try {
            namedParameterJdbcTemplate.update("delete from Car where driver_id = :driver_id", params);
        } catch (DataAccessException e) {
            log.info("Error::" + e.getMessage());
            throw e;
        }
    }
}
