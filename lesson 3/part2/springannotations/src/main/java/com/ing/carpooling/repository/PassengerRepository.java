package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
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

public class PassengerRepository implements CrudRepository<Passenger, Long> {

    private static final Logger log = LoggerFactory.getLogger(PassengerRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PASSENGER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   first_name VARCHAR(16) NOT NULL, \n" +
            "   last_name VARCHAR(16) NOT NULL, \n" +
            ");";

    private RowMapper<Passenger> mapper = new RowMapper<Passenger>() {
        @Override
        public Passenger mapRow(ResultSet resultSet, int i) throws SQLException {
            Passenger passenger = new Passenger();
            passenger.setId(resultSet.getLong("id"));
            passenger.setFirstName(resultSet.getString("first_name"));
            passenger.setLastName(resultSet.getString("last_name"));
            return passenger;
        }
    };

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PassengerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Passenger save(Passenger instance) {
        if (instance.getId()==null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = new MapSqlParameterSource()
                                            .addValue("first_name", instance.getFirstName())
                                            .addValue("last_name", instance.getLastName());

            namedParameterJdbcTemplate.update("insert into Passenger (first_name, last_name) values (:first_name, :last_name)", params, holder);
            instance.setId(holder.getKey().longValue());
        }
        return instance;
    }

    @Override
    public List<Passenger> findAll() {
        return namedParameterJdbcTemplate.query("select * from Passenger", mapper);
    }

    public List<Passenger> findAllByRide(Long rideId) {
        SqlParameterSource params = new MapSqlParameterSource().addValue(":ride_id", rideId);
        return namedParameterJdbcTemplate.query("select t1.* from Passenger t1 inner join Ride_Passenger t2 on (t1.id = t2.passenger_id) where t2.ride_id = :ride_id", params, mapper);
    }
    @Override
    public Optional<Passenger> findOne(Long id){
        SqlParameterSource params = new MapSqlParameterSource().addValue(":id", id);
        try {
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("select * from Passenger where id = :id", params, mapper)
                );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            namedParameterJdbcTemplate.update("delete from Passenger where id = :id", params);
        } catch (DataAccessException e) {
            log.info("Error::" + e.getMessage());
        }
    }
}
