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
    //region properties
    private static final Logger log = LoggerFactory.getLogger(CarRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
            "id INT NOT NULL auto_increment, \n"  +
            "number VARCHAR(8) NOT NULL, \n" +
            "seats INT NOT NULL, \n" +
            "idDriver INT REFERENCES Driver(id) \n" +
            ");";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Car> mapper = new RowMapper<Car>() {
        @Override
        public Car mapRow(ResultSet resultSet, int i)  {
            Car car = new Car();
            try {
                car.setId(resultSet.getLong("id"));
                car.setNumber(resultSet.getString("number"));
                car.setSeats(resultSet.getInt("seats"));
            }
            catch (SQLException e){
                throw new IllegalStateException("Could not map row to car",e);
            }
            return  car;
        }
    };
    //endregion

    //region Constructor
    public CarRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    //endregion

    //region CRUD implementation
    @Override
    public Car save(Car car) {
        log.info("CarRepository -> save");
        if(car.getId() == null){
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters =  new MapSqlParameterSource()
                    .addValue("number", car.getNumber())
                    .addValue("seats", car.getSeats())
                    .addValue("idDriver", car.getDriverId());
            namedParameterJdbcTemplate.update("INSERT into CAR (number, seats, idDriver) \n" +
                    "values (:number, :seats, :idDriver)",parameters, holder);
            car.setId(holder.getKey().longValue());
        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        log.info("CarRepository -> findAll");
        return namedParameterJdbcTemplate.query("select * from CAR", mapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        log.info("CarRepository -> findOne - id {}", id);
        SqlParameterSource parameters =  new MapSqlParameterSource()
                .addValue("id",id);
        try{
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("select * from CAR where id= :id", parameters, mapper));
        }catch(EmptyResultDataAccessException e){
            log.warn("CarRepository -> findOne - no car was found id: {}",id);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        log.info("CarRepository -> delete - id {}", id);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update("delete from CAR where id = :id",parameterSource);
    }
    //endregion
}
