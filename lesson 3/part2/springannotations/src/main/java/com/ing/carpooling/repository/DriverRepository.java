package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DriverRepository implements CrudRepository<Driver, Long> {

    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private final CarRepository carRepository;

    public static final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS driver ( \n" +
            "   id BIGINT NOT NULL auto_increment, \n" +
            "   firstname VARCHAR(50) NOT NULL, \n" +
            "   lastname VARCHAR(50) NOT NULL \n" +
            "); \n " +
            "   CREATE TABLE IF NOT EXISTS driver_cars ( \n" +
            "   car_id INT NOT NULL, \n" +
            "   driver_id INT NOT NULL \n" +
            "); \n" +
            "   ALTER TABLE driver_cars ADD CONSTRAINT u_driver_car UNIQUE(car_id, driver_id); \n";

    private final ResultSetExtractor<List<Driver>> resultSetExtractor = new ResultSetExtractor<List<Driver>>() {
        @Override
        public List<Driver> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Driver> map = new HashMap<>();
            Driver driver = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                driver = map.get(id);
                if (driver == null) {
                    driver = new Driver();
                    driver.setId(id);
                    driver.setFirstName(resultSet.getString("firstname"));
                    driver.setLastName(resultSet.getString("lastname"));
                    map.put(id, driver);
                }

                List<Car> carsList = driver.getCars();
                if (carsList == null) {
                    carsList = new ArrayList<>();
                    driver.setCars(carsList);
                }

                Car car = new Car();
                car.setId(resultSet.getLong("car_id"));
                car.setNumber(resultSet.getString("number"));
                car.setSeats(resultSet.getInt("seats"));
                carsList.add(car);
            }

            return new ArrayList<>(map.values());
        }
    };

    public DriverRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CarRepository carRepository) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
        this.carRepository = carRepository;
    }

    @Override
    public Driver save(Driver driver) {
        // TODO 0 implement Driver crud
        if (driver.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstname", driver.getFirstName())
                    .addValue("lastname", driver.getLastName());
            namedJdbcTemplate.update("INSERT INTO driver (firstname, lastname)\n" +
                    "values (:firstname, :lastname)", parameters, holder);
            driver.setId(holder.getKey().longValue());
            driver.getCars().forEach(car -> {
                Car savedCar = carRepository.save(car);
                SqlParameterSource parametersDriverCar = new MapSqlParameterSource()
                        .addValue("car", savedCar.getId())
                        .addValue("driver", driver.getId());
                namedJdbcTemplate.update("INSERT INTO driver_cars (car_id, driver_id)\n" +
                        "VALUES (:car, :driver)", parametersDriverCar);
            });
        } else {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstname", driver.getFirstName())
                    .addValue("lastname", driver.getLastName());
            namedJdbcTemplate.update("UPDATE driver SET firstname = :firstname \n" +
                    "lastname = :lastname)", parameters);
            driver.getCars().forEach(car -> {
                if (car.getId() == null) {
                    Car savedCar = carRepository.save(car);
                    SqlParameterSource parametersDriverCar = new MapSqlParameterSource()
                            .addValue("car", savedCar.getId())
                            .addValue("driver", driver.getId());
                    namedJdbcTemplate.update("INSERT INTO driver_cars (car_id, driver_id)\n" +
                            "VALUES (:car, :driver)", parametersDriverCar);
                } else {
                    carRepository.save(car);
                }
            });
        }
        return driver;
    }

    @Override
    public List<Driver> findAll() {
        // TODO 0 implement Driver crud
        return namedJdbcTemplate.query(
                "SELECT d.*, c.id AS car_id, c.number, c.seats " +
                                "FROM driver AS d " +
                                "JOIN driver_cars AS dc ON dc.driver_id = d.id " +
                                "JOIN car AS c ON c.id = dc.car_id",
                resultSetExtractor);
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        // TODO 0 implement Driver crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return Optional.of(
                    namedJdbcTemplate.query(
                            "SELECT d.*, c.id as car_id, c.number, c.seats " + "FROM driver AS d " +
                                        "JOIN driver_cars AS dc ON dc.driver_id = d.id " +
                                        "JOIN car AS c ON c.id = dc.car_id " +
                                        "WHERE d.id = :id",
                            parameters,
                            resultSetExtractor).get(0));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no driver found id: {}, ", id);
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM driver WHERE id = :id", parameters);
        namedJdbcTemplate.update("DELETE FROM driver_cars WHERE driver_id = :id", parameters);
    }
}
