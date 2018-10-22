package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
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

public class PassengerRepository implements CrudRepository<Passenger, Long> {
    //region properties
    private static final Logger log = LoggerFactory.getLogger(PassengerRepository.class);
    public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXITS PASSENGER (\n" +
            "id INT NOT NULL auto_increment, \n" +
            "firstName VARCHAR(20) NOT NULL, \n" +
            "lastName VARCHAR(20) NOT NULL, \n" +
            ");";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Passenger> mapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet resultSet, int i) {
            Passenger passenger = new Passenger();
            try{
                passenger.setId(resultSet.getLong("id"));
                passenger.setFirstName(resultSet.getString("firstName"));
                passenger.setLastName(resultSet.getString("lastName"));
            }catch (SQLException e) {
                throw new IllegalStateException("Could not map row to Passenger");
            }
            return passenger;
        }
    };
    //endregion

    //region Constructor
    public PassengerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    //endregion

    //region CRUD Operations
    @Override
    public Passenger save(Passenger passenger) {
        log.info("PassengerRepository -> save");
        if(passenger.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstName", passenger.getFirstName())
                    .addValue("lastName", passenger.getLastName());
            namedParameterJdbcTemplate.update("INSERT INTO PASSENGER (firstName, lastName) \n" +
                    "values (:firstName, :lastName)", parameters, holder);
            passenger.setId(holder.getKey().longValue());
         }
        return passenger;
    }

    @Override
    public List<Passenger> findAll() {
        log.info("PassengerRepository -> findAll");
        return namedParameterJdbcTemplate.query("SELECT * FROM PASSENGER", mapper);
    }

    @Override
    public Optional<Passenger> findOne(Long id){
        log.info("PassengerRepository -> findOne - id {}",id);
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("id",id);
        try{
           return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("SELECT * FROM PASSENGER WHERE id = :id", parameter, mapper));
        }catch (EmptyResultDataAccessException ex){
            log.warn("PassengerRepository -> findOne - id {} no object found", id);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        log.info("PassengerRepository -> delete - id {}", id);
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM PASSENGER WHERE id = :id", parameter);
    }
    //endregion
}
