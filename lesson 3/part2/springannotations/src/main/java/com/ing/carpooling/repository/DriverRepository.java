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

import com.ing.carpooling.domain.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRepository extends Repository implements CrudRepository<Driver, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   FIRST_NAME VARCHAR(50) NOT NULL,\n" +
        "   LAST_NAME VARCHAR(50) NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
    private final RowMapper<Driver> mapper = new RowMapper<Driver>() {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) {
            Driver driver = new Driver();
            try {
                driver.setId(resultSet.getLong("id"));
                driver.setFirstName(resultSet.getString("first_name"));
                driver.setLastName(resultSet.getString("last_name"));
            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to driver", e);
            }
            return driver;
        }
    };

    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(namedJdbcTemplate);
    }

    @Override
    public Driver save(Driver driver) {

        if (isNull(driver.getId())) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", driver.getFirstName())
                .addValue("lastName", driver.getLastName());

            namedJdbcTemplate.update("insert into driver (first_name, last_name) values (:firstName, :lastName)", sqlParameterSource, keyHolder);
            driver.setId(keyHolder.getKey().longValue());

        }
        return driver;
    }

    @Override
    public List<Driver> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from Driver", mapper);
    }

    @Override
    public Optional<Driver> findOne(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select * from driver where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no driver found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        namedJdbcTemplate.update("delete from driver where id = :id", parameters);
    }
}
