package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.RideRequest;
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

public class RideRequestRepository implements CrudRepository<RideRequest, Long> {

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private final RideRepository rideRepository;

    private final PassengerRepository passengerRepository;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE_REQUEST ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   passenger_id INT NOT NULL,\n" +
            "   ride_id INT NOT NULL,\n" +
            "   status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELED') NOT NULL, \n" +
            ");";

    public RideRequestRepository(NamedParameterJdbcTemplate namedJdbcTemplate, PassengerRepository passengerRepository, RideRepository rideRepository) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.passengerRepository = passengerRepository;
        this.rideRepository = rideRepository;
    }

    private final RowMapper<RideRequest> mapper = new RowMapper<RideRequest>() {
        @Override
        public RideRequest mapRow(ResultSet resultSet, int i) {
            RideRequest rideRequest = new RideRequest();
            try {
                rideRequest.setId(resultSet.getLong("id"));
                passengerRepository.findOne(resultSet.getLong("passenger_id")).ifPresent(passenger -> {
                    rideRequest.setPassenger(passenger);
                });
                rideRepository.findOne(resultSet.getLong("id")).ifPresent(ride -> {
                    rideRequest.setRide(ride);
                });
                rideRequest.setStatus(RideRequest.Status.valueOf(resultSet.getString("status")));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to ride request", e);
            }
            return rideRequest;
        }
    };

    @Override
    public RideRequest save(RideRequest rideRequest) {
        // TODO 0 implement RideRequest crud
        if (rideRequest.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("passenger", rideRequest.getPassenger().getId())
                    .addValue("ride", rideRequest.getRide().getId());
            namedJdbcTemplate.update("INSERT INTO RIDE_REQUEST (passenger_id, ride_id)\n" +
                    "VALUES (:passenger, :ride)", parameters, holder);
            rideRequest.setId(holder.getKey().longValue());
        }
        return rideRequest;
    }

    @Override
    public List<RideRequest> findAll() {
        // TODO 0 implement RideRequest crud
        log.info("findAll");
        return namedJdbcTemplate.query("SELECT * FROM RIDE_REQUEST", mapper);
    }

    @Override
    public Optional<RideRequest> findOne(Long id) {
        // TODO 0 implement RideRequest crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("SELECT * FROM RIDE_REQUEST WHERE id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no ride request found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement RideRequest crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM RIDE_REQUEST WHERE id = :id", parameters);
    }
}
