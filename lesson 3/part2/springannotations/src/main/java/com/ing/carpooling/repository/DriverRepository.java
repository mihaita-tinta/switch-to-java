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

    public static final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstname VARCHAR(50) NOT NULL, \n" +
            "   lastname VARCHAR(50) NOT NULL \n" +
            "); \n " +
            "   CREATE TABLE IF NOT EXISTS DRIVER_CARS ( \n" +
            "   car_id INT NOT NULL, \n" +
            "   driver_id INT NOT NULL \n" +
            "); \n" +
            "   ALTER TABLE DRIVER_CARS ADD CONSTRAINT DRIVER_CAR UNIQUE(car_id, driver_id); \n";

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
            namedJdbcTemplate.update("INSERT INTO DRIVER (firstname, lastname)\n" +
                    "values (:firstname, :lastname)", parameters, holder);
            driver.setId(holder.getKey().longValue());
            driver.getCars().forEach(car -> {
                Car savedCar = carRepository.save(car);
                SqlParameterSource parametersDriverCar = new MapSqlParameterSource()
                        .addValue("car_id", savedCar.getId())
                        .addValue("driver_id", driver.getId());
                namedJdbcTemplate.update("INSERT INTO DRIVER_CARS (car_id, driver_id)\n" +
                        "VALUES (:car_id, :driver_id)", parametersDriverCar);
            });
        }
        return driver;
    }

    @Override
    public List<Driver> findAll() {
        // TODO 0 implement Driver crud (to be modified :< )
        return namedJdbcTemplate.query(
                "SELECT d.*, c.id as car_id, c.number, c.seats " +
                                "FROM DRIVER AS d " +
                                "JOIN DRIVER_CARS AS dc ON dc.driver_id = d.id " +
                                "JOIN CAR AS c ON c.id = dc.car_id",
                resultSetExtractor);
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        // TODO 0 implement Driver crud
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.of(
                namedJdbcTemplate.query(
                        "SELECT d.*, c.id as car_id, c.number, c.seats " + "FROM DRIVER AS d " +
                                    "JOIN DRIVER_CARS AS dc ON dc.driver_id = d.id " +
                                    "JOIN CAR AS c ON c.id = dc.car_id " +
                                    "WHERE d.id = :id",
                        parameters,
                        resultSetExtractor).get(id.intValue()));
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement Driver crud
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("DELETE FROM DRIVER WHERE id = :id", parameters);
        namedJdbcTemplate.update("DELETE FROM DRIVER_CARS WHERE driver_id = :id", parameters);
    }
}
