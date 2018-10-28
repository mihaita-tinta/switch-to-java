package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Location;
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
    private static final Logger log = LoggerFactory.getLogger(PassengerRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PASSENGER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstName VARCHAR(20) NOT NULL,\n" +
            "   lastName VARCHAR(20) NOT NULL,\n" +
            "   rideId INT \n"+
            ");";

    private final RowMapper<Passenger> mapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet resultSet, int i) {
            Passenger passenger = new Passenger();
            try {
                passenger.setId(resultSet.getLong("id"));
                passenger.setFirstName(resultSet.getString("firstName"));
                passenger.setLastName(resultSet.getString("lastName"));
                passenger.setRideId(resultSet.getLong("rideId"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to passenger", e);
            }
            return passenger;
        }
    };
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public PassengerRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }








    // ##################### CRUD ######################

    @Override
    public Passenger save(Passenger passenger) {
        if (passenger.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstName", passenger.getFirstName())
                    .addValue("lastName", passenger.getLastName())
                    .addValue("rideId",passenger.getRideId());
            namedJdbcTemplate.update("insert into passenger (firstName, lastName)\n" +
                    "values (:firstName, :lastName)", parameters, holder);
            passenger.setId(holder.getKey().longValue());
        }
        else{
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id",passenger.getId())
                    .addValue("firstName", passenger.getFirstName())
                    .addValue("lastName", passenger.getLastName())
                    .addValue("rideId",passenger.getRideId());
            namedJdbcTemplate.update("UPDATE passenger SET firstName = :firstName, lastName = :lastName, rideId = :rideId\n" +
                    " WHERE id =:id", parameters);
        }
        return passenger;
    }

    @Override
    public List<Passenger> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from passenger", mapper);
    }

    @Override
    public Optional<Passenger> findOne(Long id){
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("select * from passenger where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no passenger found id: {}, ", id);
            return Optional.empty();
        }
    }


    public List<Passenger> findPassengerByRide(Long rideId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("rideId", rideId);
        log.info("Find passengers by ride");
        return namedJdbcTemplate.query("select * from passenger where rideId = :rideId", parameters, mapper);
    }


    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("delete from passenger where id = :id", parameters);
    }
}
