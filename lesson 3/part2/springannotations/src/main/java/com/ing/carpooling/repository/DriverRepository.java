package com.ing.carpooling.repository;

import com.ing.carpooling.config.DatabaseConfig;
import com.ing.carpooling.config.PropertiesConfig;
import com.ing.carpooling.config.RepositoryConfig;
import com.ing.carpooling.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements CrudRepository<Driver, Long> {
    //region properties
    private static final Logger log = LoggerFactory.getLogger(DriverRepository.class);
    public static final String CREATE_TABEL = "CREATE TABLE IF NOT EXISTS DRIVER (\n" +
            " id INT NOT NULL auto_increment, \n" +
            " firstName VARCHAR(20) NOT NULL, \n" +
            " lastName VARCHAR(20) NOT NULL" +
            ")";
    AnnotationConfigApplicationContext context;

    private final RowMapper<Driver> mapper =  new RowMapper<Driver>() {
        @Override
        public Driver mapRow(ResultSet resultSet, int i) throws SQLException {
            Driver driver = new Driver();
            try {
                driver.setId(resultSet.getLong("id"));
                driver.setFirstName(resultSet.getString("firstName"));
                driver.setLastName(resultSet.getString("lastName"));
            }catch (SQLException e) {
                throw  new IllegalStateException("Could not map row to Driver");
            }
            return  driver;
        }
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    //endRegion

    //region Constructor
    public DriverRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    //endregion


    //region CRUD
    @Override
    public Driver save(Driver driver) {
        log.info("DriverRepository -> save");
        context = new AnnotationConfigApplicationContext(PropertiesConfig.class, DatabaseConfig.class,
                RepositoryConfig.class);
        CarRepository carRepository = context.getBean(CarRepository.class);

        if(driver.getId() == null){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstName", driver.getFirstName())
                    .addValue("lastName", driver.getLastName());
            namedParameterJdbcTemplate.update("insert into DRIVER (firstName, lastName)\n" +
                    "values(:firstName, :lastName)", parameters, keyHolder);
            driver.setId(keyHolder.getKey().longValue());
            if(driver.getCars() != null)
            {    driver.getCars().forEach(car ->
                 {  car.setDriverId(driver.getId());
                    carRepository.save(car);});
            }
        }
        return driver;
    }

    @Override
    public List<Driver> findAll() {
        log.info("DriveRepository -> findAll");
        return namedParameterJdbcTemplate.query("select * from Driver", mapper);
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        log.info("DriverRepository -> findOne - id {}", id);
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("id", id);
        try{
            return Optional.of(
                    namedParameterJdbcTemplate.queryForObject("select * from DRIVER d join CAR c on c.idDriver = d.id where d.id = :id", parameter, mapper));
        }catch (EmptyResultDataAccessException e){
            log.info("DriverRepository -> findOne - id {} no Drive found", id);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        log.info("DriverRepository -> delete - id {}", id);
        SqlParameterSource paramater = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update("delete from DRIVER where id = :id", paramater);
    }
    //endregion
}
