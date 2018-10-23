package com.ing.carpooling.repository;

import com.ing.carpooling.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS car ( \n" +
            "   id BIGINT NOT NULL auto_increment, \n" +
            "   number VARCHAR(7) NOT NULL, \n" +
            "   seats INT NOT NULL, \n" +
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
    public Car save(Car car) {
        // TODO 0 implement Car crud
        if (car.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("number", car.getNumber())
                    .addValue("seats", car.getSeats());
            namedJdbcTemplate.update("INSERT INTO car (number, seats)\n" +
                    "values (:number, :seats)", parameters, holder);
            car.setId(holder.getKey().longValue());
        } else {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id", car.getId())
                    .addValue("number", car.getNumber())
                    .addValue("seats", car.getSeats());
            namedJdbcTemplate.update("UPDATE car SET number = :number, seats = :seats \n" +
                    "WHERE id = :id", parameters);
        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        // TODO 0  implement Car crud
        log.info("findAll");
        return namedJdbcTemplate.query("SELECT * FROM car", mapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        // TODO 0  implement Car crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("SELECT * FROM car WHERE id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no car found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Car crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM car WHERE id = :id", parameters);
    }
}
