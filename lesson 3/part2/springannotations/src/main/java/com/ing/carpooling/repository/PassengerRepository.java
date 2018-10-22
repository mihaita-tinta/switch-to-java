package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
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

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PASSENGER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstname VARCHAR(50) NOT NULL, \n" +
            "   lastname VARCHAR(50) NOT NULL, \n" +
            ");";

    public PassengerRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private final RowMapper<Passenger> mapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet resultSet, int i) {
            Passenger passenger = new Passenger();
            try {
                passenger.setId(resultSet.getLong("id"));
                passenger.setFirstName(resultSet.getString("firstname"));
                passenger.setLastName(resultSet.getString("lastname"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to passenger", e);
            }
            return passenger;
        }
    };

    @Override
    public Passenger save(Passenger passenger) {
        // TODO 0 implement Passenger crud
        if (passenger.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstname", passenger.getFirstName())
                    .addValue("lastname", passenger.getLastName());
            namedJdbcTemplate.update("INSERT INTO PASSENGER (firstname, lastname)\n" +
                    "VALUES (:firstname, :lastname)", parameters, holder);
            passenger.setId(holder.getKey().longValue());
        }
        return passenger;
    }

    @Override
    public List<Passenger> findAll() {
        // TODO 0 implement Passenger crud
        log.info("findAll");
        return namedJdbcTemplate.query("SELECT * FROM PASSENGER", mapper);
    }

    @Override
    public Optional<Passenger> findOne(Long id){
        // TODO 0 implement Passenger crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("SELECT * FROM PASSENGER WHERE id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no passenger found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Passenger crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM PASSENGER WHERE id = :id", parameters);
    }
}
