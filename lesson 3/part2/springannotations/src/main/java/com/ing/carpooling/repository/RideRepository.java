package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Location;
import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private final CarRepository carRepository;

    private final LocationRepository locationRepository;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   location_from INT NOT NULL,\n" +
            "   location_to INT NOT NULL,\n" +
            "   when TIMESTAMP WITH TIME ZONE NOT NULL, \n" +
            "   car_id VARCHAR(20) NOT NULL, \n" +
            "   status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELED') NOT NULL, \n" +
            ");";


    public RideRepository(NamedParameterJdbcTemplate namedJdbcTemplate, CarRepository carRepository, LocationRepository locationRepository) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
    }

    private final RowMapper<Ride> mapper = new RowMapper<Ride>() {
        @Override
        public Ride mapRow(ResultSet resultSet, int i) {
            Ride ride = new Ride();
            try {
                locationRepository.findOne(resultSet.getLong("location_from")).ifPresent(location -> {
                    ride.setFrom(location);
                });
                locationRepository.findOne(resultSet.getLong("location_from")).ifPresent(location -> {
                    ride.setTo(location);
                });
                carRepository.findOne(resultSet.getLong("car_id")).ifPresent(car -> {
                    ride.setCar(car);
                });
                ride.setWhen(ZonedDateTime.parse(resultSet.getString("when")));
                ride.setStatus(Ride.Status.valueOf(resultSet.getString("status")));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to ride", e);
            }
            return ride;
        }
    };

    @Override
    public Ride save(Ride ride) {
        // TODO 0 implement Ride crud
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("location_from", ride.getFrom().getId())
                .addValue("location_to", ride.getTo().getId())
                .addValue("when", ride.getWhen())
                .addValue("car", ride.getCar().getId())
                .addValue("status", ride.getStatus());
        namedJdbcTemplate.update("INSERT INTO RIDE (location_from, location_to, when, car_id, status)\n" +
                "values (:location_from, :location_to, :when, :car, :status)", parameters);
        ride.setId(holder.getKey().longValue());

        return ride;
    }

    @Override
    public List<Ride> findAll() {
        // TODO 0 implement Ride crud
        log.info("findAll");
        return namedJdbcTemplate.query("SELECT * FROM RIDE", mapper);
    }

    @Override
    public Optional<Ride> findOne(Long id) {
        // TODO 0 implement Ride crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("SELECT * FROM RIDE WHERE id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no ride found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Ride crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM RIDE WHERE id = :id", parameters);
    }
}
