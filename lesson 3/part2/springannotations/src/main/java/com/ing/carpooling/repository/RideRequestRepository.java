package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import com.ing.carpooling.domain.RideRequest;
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

public class RideRequestRepository implements CrudRepository<RideRequest, Long> {


    private static final Logger log = LoggerFactory.getLogger(RideRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE_REQUEST ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   ride_id INT NOT NULL, \n" +
            "   passenger_id INT NOT NULL, \n" +
            "   status VARCHAR(16) NOT NULL \n" +
            ");";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RideRepository rideRepository;
    private PassengerRepository passengerRepository;

    public RideRequestRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        rideRepository                  = new RideRepository(namedParameterJdbcTemplate);
    }

    private RowMapper<RideRequest> mapper = new RowMapper<RideRequest>() {
        @Override
        public RideRequest mapRow(ResultSet resultSet, int i) throws SQLException {
            RideRequest rideRequest = new RideRequest();

            rideRequest.setId(resultSet.getLong("id"));
            rideRequest.setStatus(RideRequest.Status.valueOf(resultSet.getString("status")));

            Passenger passenger = new Passenger();
            passenger.setId(resultSet.getLong("passenger_id"));
            passenger.setFirstName(resultSet.getString("passenger_first_name"));
            passenger.setLastName(resultSet.getString("passenger_last_name"));
            rideRequest.setPassenger(passenger);

            Ride ride = rideRepository.findOne(resultSet.getLong("ride_id")).orElse(new Ride());
            rideRequest.setRide(ride);
            return rideRequest;
        }
    };

    @Override
    public RideRequest save(RideRequest instance) {
        if (instance.getId()==null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue(":passenger_id", instance.getPassenger().getId())
                    .addValue(":ride_id", instance.getRide().getId())
                    .addValue(":status", instance.getStatus().toString());
            namedParameterJdbcTemplate.update("insert into RIDE_REQUEST (passenger_id, ride_id, status) values (:passenger_id, :ride_id, :status)", params, holder);
            instance.setId(holder.getKey().longValue());
        }
        return instance;
    }

    @Override
    public List<RideRequest> findAll() {
        return namedParameterJdbcTemplate.query("select t1.*, t2.id as passenger_id, t2.last_name as passenger_last_name, t2.first_name as passenger_first_name from RIDE_REQUEST t1 left join PASSENGER t2 on (t1.passenger_id = t2.id)", mapper);
    }

    @Override
    public Optional<RideRequest> findOne(Long id) {
        try {
            SqlParameterSource params = new MapSqlParameterSource().addValue(":id", id);
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("\"select t1.*, t2.id as passenger_id, t2.last_name as passenger_last_name, t2.first_name as passenger_first_name from RIDE_REQUEST t1 left join PASSENGER t2 on (t1.passenger_id = t2.id) where t1.id = :id", params, mapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            namedParameterJdbcTemplate.update("delete from RIDE_REQUEST where id = :id", params);
        } catch (DataAccessException e) {
            log.info("Error::" + e.getMessage());
        }
    }
}
