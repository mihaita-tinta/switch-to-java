package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Location;
import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   from_id INT NOT NULL, \n" +
            "   to_id INT NOT NULL, \n" +
            "   when TIMESTAMP NOT NULL, \n" +
            "   car_id INT NOT NULL, \n" +
            "   status VARCHAR(16) NOT NULL \n" +
            ");";
    public static final String CREATE_TABLE_PASSENGERS = "CREATE TABLE IF NOT EXISTS RIDE_PASSENGER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   ride_id INT NOT NULL, \n" +
            "   passenger_id INT NOT NULL \n" +
            ");";

    private RowMapper<Ride> mapper = new RowMapper<Ride>() {
        @Override
        public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
            Ride ride = new Ride();
            if (ride.getId()==null) {
                ride.setId(resultSet.getLong("id"));
                // Repository pot fi inlocuite cu un left joi intre Ride, Location, Car tables
                Location from   = locationRepository.findOne(resultSet.getLong("from_id")).orElse(new Location());
                ride.setFrom(from);

                Location to     = locationRepository.findOne(resultSet.getLong("to_id")).orElse(new Location());
                ride.setTo(to);

                Car car         = carRepository.findOne(resultSet.getLong("car_id")).orElse(new Car());
                ride.setCar(car);

                List<Passenger> passengers = passengerRepository.findAllByRide(ride.getId());
                ride.setPassengers(passengers);

                ride.setStatus(Ride.Status.valueOf(resultSet.getString("status")));

                Timestamp timestamp = resultSet.getTimestamp("when");
                ride.setWhen(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneOffset.UTC));
            }
            return ride;
        }
    };

    private static final Logger log = LoggerFactory.getLogger(RideRepository.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private LocationRepository locationRepository;
    private CarRepository carRepository;
    private PassengerRepository passengerRepository;

    public RideRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        locationRepository              = new LocationRepository(namedParameterJdbcTemplate);
        carRepository                   = new CarRepository(namedParameterJdbcTemplate);
        passengerRepository             = new PassengerRepository(namedParameterJdbcTemplate);
    }

    @Override
    public Ride save(Ride instance) {

        if (instance!=null) {
            KeyHolder holder = new GeneratedKeyHolder();
            Location from = instance.getFrom();
            if (from.getId()==null) {
                from = locationRepository.save(from);
            }
            Location to = instance.getTo();
            if (to.getId()==null) {
                to = locationRepository.save(to);
            }
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue(":from_id", from.getId())
                    .addValue(":to_id", to.getId())
                    .addValue(":car_id", instance.getCar().getId())
                    .addValue(":when", Timestamp.from(instance.getWhen().toInstant()))
                    .addValue(":status", instance.getStatus().toString());

            namedParameterJdbcTemplate.update("insert into Ride (from_id, to_id, when, car_id, status) values (:from_id, :to_id, :when, :car_id, :status", params, holder);

            instance.setId(holder.getKey().longValue());

            for (Passenger passenger : instance.getPassengers()) {
                SqlParameterSource passengerParams = new MapSqlParameterSource()
                        .addValue(":ride_id", instance.getId())
                        .addValue(":passenger_id", passenger.getId());
                namedParameterJdbcTemplate.update("insert into Ride_Panssanger (ride_id, passenger_id) values (:ride_id, :passenger_id)", params);
            }
        }
        return instance;
    }

    @Override
    public List<Ride> findAll() {
        return namedParameterJdbcTemplate.query("select * from Ride", mapper);
    }

    @Override
    public Optional<Ride> findOne(Long id) {
        try {
            SqlParameterSource params = new MapSqlParameterSource().addValue(":id", id);
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("select * from Ride where id = :id", params, mapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            namedParameterJdbcTemplate.update("delete from Ride where id = :id", params);
            namedParameterJdbcTemplate.update("delete from Ride_Passenger where ride_id = :id", params);
        } catch (DataAccessException e) {
            log.info("Error::" + e.getMessage());
        }
    }
}
