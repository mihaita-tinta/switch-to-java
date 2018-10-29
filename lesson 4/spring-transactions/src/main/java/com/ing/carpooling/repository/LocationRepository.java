package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LocationRepository implements CrudRepository<Location, Long> {
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS LOCATION ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   latitude DOUBLE NOT NULL,\n" +
            "   longitude DOUBLE NOT NULL,\n" +
            "   address VARCHAR(50) NOT NULL, \n" +
            "   city VARCHAR(20) NOT NULL, \n" +
            "   state VARCHAR(20) NOT NULL, \n" +
            "   zip VARCHAR(20) NOT NULL\n" +
            ");";

    private final RowMapper<Location> mapper = new RowMapper<Location>() {
        @Override
        public Location mapRow(ResultSet resultSet, int i) {
            Location location = new Location();
            try {
                location.setId(resultSet.getLong("id"));
                location.setLatitude(resultSet.getDouble("latitude"));
                location.setLongitude(resultSet.getDouble("longitude"));
                location.setAddress(resultSet.getString("address"));
                location.setCity(resultSet.getString("city"));
                location.setState(resultSet.getString("state"));
                location.setZip(resultSet.getString("zip"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to location", e);
            }
            return location;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public LocationRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Location save(Location location) {
        if (location.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("latitude", location.getLatitude())
                    .addValue("longitude", location.getLongitude())
                    .addValue("address", location.getAddress())
                    .addValue("city", location.getCity())
                    .addValue("state", location.getState())
                    .addValue("zip", location.getZip());
            namedJdbcTemplate.update("insert into location (latitude, longitude, address, city, state, zip)\n" +
                    "values (:latitude, :longitude, :address, :city, :state, :zip)", parameters, holder);
            location.setId(holder.getKey().longValue());
        }
        return location;
    }

    @Override
    public Optional<Location> findOne(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                                            .addValue("id", id);
        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select * from Location where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no location found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Location> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from location", mapper);
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("id", id);
        namedJdbcTemplate.update("delete from location where id = :id", parameters);
    }
}
