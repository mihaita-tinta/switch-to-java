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

import com.ing.carpooling.domain.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerRepository extends Repository implements CrudRepository<Passenger, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PASSENGER ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   FIRST_NAME VARCHAR(50) NOT NULL,\n" +
        "   LAST_NAME VARCHAR(50) NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
    private final RowMapper<Passenger> mapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet resultSet, int i) {
            Passenger passenger = new Passenger();
            try {
                passenger.setId(resultSet.getLong("id"));
                passenger.setFirstName(resultSet.getString("first_name"));
                passenger.setLastName(resultSet.getString("last_name"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to driver", e);
            }
            return passenger;
        }
    };


    public PassengerRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(namedJdbcTemplate);
    }

    @Override
    public Passenger save(Passenger passenger) {

        if (isNull(passenger.getId())) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", passenger.getFirstName())
                .addValue("lastName", passenger.getLastName());

            namedJdbcTemplate.update("insert into Passenger (first_name, last_name) values (:firstName, :lastName)", sqlParameterSource, keyHolder);
            passenger.setId(keyHolder.getKey().longValue());

        }
        return passenger;
    }

    @Override
    public List<Passenger> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from Passenger", mapper);
    }

    @Override
    public Optional<Passenger> findOne(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select * from Passenger where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no passenger found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        namedJdbcTemplate.update("delete from Passenger where id = :id", parameters);
    }
}
