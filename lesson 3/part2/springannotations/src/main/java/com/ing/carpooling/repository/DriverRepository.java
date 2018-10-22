package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Driver;
import com.ing.carpooling.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements CrudRepository<Driver, Long> {
    private static final Logger log = LoggerFactory.getLogger(DriverRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstName VARCHAR(20) NOT NULL,\n" +
            "   secondName VARCHAR(20) NOT NULL,\n" +
            //"   cars "
            ");";

    private final RowMapper<Driver> mapper = new RowMapper<Driver>() {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) {
            Driver driver = new Driver();
            try {
                driver.setId(resultSet.getLong("id"));
                driver.setFirstName(resultSet.getString("firstName"));
                driver.setLastName(resultSet.getString("secondName"));
                //driver.setCars(resultSet.getString("cars"));

            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to location", e);
            }
            return driver;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Driver save(Driver instance) {
        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public List<Driver> findAll() {
        // TODO 0 implement Driver crud
        return null;
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        // TODO 0 implement Driver crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud

    }
}
