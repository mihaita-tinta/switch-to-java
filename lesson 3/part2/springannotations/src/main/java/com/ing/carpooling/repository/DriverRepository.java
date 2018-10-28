package com.ing.carpooling.repository;

import static java.util.Objects.isNull;

import java.util.ArrayList;
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

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRepository extends Repository implements CrudRepository<Driver, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   FIRST_NAME VARCHAR(50) NOT NULL,\n" +
        "   LAST_NAME VARCHAR(50) NOT NULL, \n" +
        ");";

    public static final String CREATE_TABLE_DRIVER_CAR = "CREATE TABLE IF NOT EXISTS DRIVER_CAR ( \n" +
        "   CARID INT NOT NULL,\n" +
        "   DRIVERID INT NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);
    private final CarRepository carRepository;

    private final RowMapper<List<Driver>> mapper = new RowMapper<List<Driver>>() {
        @Override
        public List<Driver> mapRow(ResultSet resultSet, int i) {
            Driver driver = null;
            List<Car> cars = new ArrayList<>();
            List<Integer> driversIds = new ArrayList<>();
            List<Driver> drivers = new ArrayList<>();

            try {

                while (resultSet.next()) {
                    if (!driversIds.contains(resultSet.getLong("driver_id"))) {

                        driver = new Driver();

                        driver.setId(resultSet.getLong("driver_id"));
                        driver.setFirstName(resultSet.getString("first_name"));
                        driver.setLastName(resultSet.getString("last_name"));
                        drivers.add(driver);
                    }

                    Car car = new Car();
                    car.setId(resultSet.getLong("car_id"));
                    car.setNumber(resultSet.getString("number"));
                    car.setSeats(resultSet.getInt("seats"));

                    cars.add(car);
                    driver.setCars(cars);
                }


            } catch (SQLException e) {
                throw new IllegalStateException("Could not map row to driver", e);
            }
            return drivers;
        }
    };

    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate, CarRepository carRepository) {
        super(namedJdbcTemplate);
        this.carRepository = carRepository;
    }

    @Override
    public Driver save(Driver driver) {

        if (isNull(driver.getId())) {

            saveCar(driver);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", driver.getFirstName())
                .addValue("lastName", driver.getLastName());

            namedJdbcTemplate.update("insert into driver (first_name, last_name) values (:firstName, :lastName)", sqlParameterSource, keyHolder);
            driver.setId(keyHolder.getKey().longValue());

            saveDriverCars(driver);

        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", driver.getId())
                .addValue("firstName", driver.getFirstName())
                .addValue("lastName", driver.getLastName());

            namedJdbcTemplate.update("UPDATE driver SET firstname = :firstname lastname = :lastname WHERE id = :id)", sqlParameterSource);
            saveDriverCars(driver);
        }
        return driver;
    }

    private void saveDriverCars(Driver driver) {
        for (Car car : driver.getCars()) {
            Car savedCar = carRepository.save(car);

            SqlParameterSource sqlParameterSourceDriverCar = new MapSqlParameterSource()
                .addValue("carId", savedCar.getId())
                .addValue("driverId", driver.getId());
            namedJdbcTemplate.update("insert into driver_car (carId, driverId) values (:carId, :driverId)", sqlParameterSourceDriverCar);
        }
    }

    private void saveCar(Driver driver) {

        for (Car car : driver.getCars()) {
            if (isNull(carRepository.findOne(car.getId()).get())) {

                carRepository.save(car);

            }
        }

    }

    @Override
    public List<Driver> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select d.id as driver_id, d.first_name, d.last_name, c.id as car_id, c.number, c.seats " +
            "from driver as d inner join driver_car as dc on d.id = dc.driverid inner join car as c on c.id = dc.carid", mapper);
    }

    @Override
    public Optional<Driver> findOne(Long id) {

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            return Optional.of(
                namedJdbcTemplate.queryForObject("select d.id as driver_id, d.first_name, d.last_name, c.id as car_id, c.number, c.seats " +
                    "from driver as d inner join driver_car as dc on d.id = dc.driverid " +
                    "inner join car as c on c.id = dc.carid where d.id = :id", parameters, mapper).get(0));
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
        namedJdbcTemplate.update("delete from driver_car where driverId = :id", parameters);

    }
}
