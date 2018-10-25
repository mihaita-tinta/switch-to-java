package com.ing.carpooling.repository;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ing.carpooling.domain.Car;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRepository extends Repository implements CrudRepository<Car, Long> {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   NUMBER VARCHAR(50) NOT NULL,\n" +
        "   SEATS INT NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
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


    public CarRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(namedJdbcTemplate);
    }

    @Override
    public Car save(Car car) {

        if (isNull(car.getId())) {
            KeyHolder holder = new GeneratedKeyHolder();

            SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("number", car.getNumber())
                .addValue("seats", car.getSeats());

            namedJdbcTemplate.update("insert into car (number, seats) values (:number, :seats)", parameters, holder);
            car.setId(holder.getKey().longValue());
        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from Car", mapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select * from Car where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no car found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        namedJdbcTemplate.update("delete from Car where id = :id", parameters);
    }
}
