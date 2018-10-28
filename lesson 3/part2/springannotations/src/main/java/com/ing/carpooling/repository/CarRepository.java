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
    private static final Logger log = LoggerFactory.getLogger(CarRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   number VARCHAR(15) NOT NULL,\n" +
            "   seats INT NOT NULL,\n" +
            "   driverId INT , \n" +
            ");";

    private final RowMapper<Car> mapper = new RowMapper<Car>() {
        @Override
        public Car mapRow(ResultSet resultSet, int i) {
            Car car = new Car();
            try {
                car.setId(resultSet.getLong("id"));
                car.setNumber(resultSet.getString("number"));
                car.setSeats(resultSet.getInt("seats"));
                car.setDriverId(resultSet.getLong("driverId"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to location", e);
            }
            return car;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CarRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {

        this.namedJdbcTemplate = namedJdbcTemplate;
    }






    // ##################### CRUD ######################

    @Override
    public Car save(Car car) {
        if (car.getId() == null) {
            System.out.println("Am intrat pe if");
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("number", car.getNumber())
                    .addValue("seats", car.getSeats())
                    .addValue("driverId", car.getDriverId());
            namedJdbcTemplate.update("insert into car (number, seats, driverId)\n" +
                    "values (:number, :seats, :driverId)", parameters, holder);
            car.setId(holder.getKey().longValue());
        }
        else{
            System.out.println("Am intrat pe else");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id",car.getId())
                    .addValue("number",car.getNumber())
                    .addValue("seats",car.getSeats())
                    .addValue("driverId",car.getDriverId());
            namedJdbcTemplate.update("UPDATE car SET number = :number, seats = :seats, driverId = :driverId\n" +
                    " WHERE id =:id", parameters);

        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from car", mapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("select * from car where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no car found id: {}, ", id);
            return Optional.empty();
        }
    }


    public List<Car> findCarsByDriverId(Long driverId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("driverId", driverId);
        log.info("Find cars by driver");
        return namedJdbcTemplate.query("select * from car where driverId = :driverId", parameters, mapper);
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("delete from car where id = :id", parameters);
    }


}
