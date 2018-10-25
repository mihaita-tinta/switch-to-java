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

import com.ing.carpooling.domain.Ride;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class RideRepository extends Repository implements CrudRepository<Ride, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   LOCATION_FROM INT NOT NULL,\n" +
        "   LOCATION_TO INT NOT NULL,\n" +
        "   WHEN TIMESTAMP NOT NULL, \n" +
        "   CAR_ID VARCHAR(20) NOT NULL, \n" +
        "   STATUS ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELED') NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final RowMapper<Ride> mapper = new RowMapper<Ride>() {
        @Override
        public Ride mapRow(ResultSet resultSet, int i) {
            Ride ride = new Ride();
            try {
                ride.setFrom(locationRepository.findOne(resultSet.getLong("location_from")).get());
                ride.setTo(locationRepository.findOne(resultSet.getLong("location_to")).get());
                ride.setWhen(ZonedDateTime.parse(resultSet.getString("when")));
                ride.setCar(carRepository.findOne(resultSet.getLong("car_id")).get());
                ride.setStatus(Ride.Status.valueOf(resultSet.getString("status")));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to ride", e);
            }
            return ride;
        }
    };

    public RideRepository(NamedParameterJdbcTemplate namedJdbcTemplate, CarRepository carRepository, LocationRepository locationRepository) {
        super(namedJdbcTemplate);
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Ride save(Ride ride) {
        if (isNull(ride.getId())) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("location_from", ride.getFrom())
                .addValue("location_to", ride.getTo())
                .addValue("when", ride.getWhen())
                .addValue("car_id", ride.getCar().getId())
                .addValue("status", ride.getStatus());

            namedJdbcTemplate.update("insert into ride (location_from, location_to, when, car_id, status)\n" +
                "values (:location_from, :location_to, :when, :car_id, :status)", parameters, holder);
            ride.setId(holder.getKey().longValue());
        }
        return ride;
    }

    @Override
    public List<Ride> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from ride", mapper);
    }

    @Override
    public Optional<Ride> findOne(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select * from ride where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no ride found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        namedJdbcTemplate.update("delete from Ride where id = :id", parameters);

    }
}
