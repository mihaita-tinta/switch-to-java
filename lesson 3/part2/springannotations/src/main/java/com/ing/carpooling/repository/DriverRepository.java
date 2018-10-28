package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import com.ing.carpooling.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DriverRepository implements CrudRepository<Driver, Long> {
    private static final Logger log = LoggerFactory.getLogger(DriverRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstName VARCHAR(20) NOT NULL,\n" +
            "   lastName VARCHAR(20) NOT NULL,\n" +
            ");";

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private CarRepository carRepository;
//http://forum.spring.io/forum/spring-projects/data/jdbc/746924-resultsetextractor-for-one-to-many-and-join-column-query

        public List<Driver> getAllDriverCar() {
            //String sql = "SELECT driver.id, firstName, lastName, car.id, number, seats FROM driver LEFT JOIN car ON car.driverId = driver.id";
            String sql = "SELECT driver.id, driver.firstName, driver.lastName, car.id, car.number, car.seats from driver LEFT JOIN car ON driver.id = car.driverid";

        return namedJdbcTemplate.query(sql, new DriverwithCarExtractor());
    }


    private static final class DriverwithCarExtractor implements ResultSetExtractor<List<Driver>> {
        @Override
        public List<Driver> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Driver> map = new HashMap<>();

            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                Driver driver = map.get(id);
                if(driver == null){
                    driver =new Driver();
                    driver.setId(id);
                    driver.setFirstName(resultSet.getString("firstName"));
                    driver.setLastName(resultSet.getString("lastName"));
                    map.put(id,driver);
                }

                List<Car> carList = driver.getCars();
                if (carList == null) {
                    carList = new ArrayList<>();
                    driver.setCars(carList);
                }

                Car car =new Car();
                car.setId(resultSet.getLong("car.id"));
                car.setSeats(resultSet.getInt("seats"));
                car.setNumber(resultSet.getString("number"));
                car.setDriverId(resultSet.getLong("driver.id"));
                carList.add(car);
                driver.setCars(carList);

            }
            return new ArrayList<>(map.values());
            }
        }

    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate, CarRepository carRepository) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.carRepository = carRepository;
    }

    @Override
    public Driver save(Driver driver) {
        if(driver.getId() == null){
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstName", driver.getFirstName())
                    .addValue("lastName", driver.getLastName());

            namedJdbcTemplate.update("insert into driver (firstName, lastName) values (:firstName, :lastName)", parameters, holder);

            driver.setId(holder.getKey().longValue());
            driver.getCars().forEach(car -> carRepository.save(car));

        }
        else{
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id",driver.getId())
                    .addValue("firstName", driver.getFirstName())
                    .addValue("lastName", driver.getLastName());
            namedJdbcTemplate.update("UPDATE driver SET firstName = :firstName, lastName = :lastName\n" +
                    "WHERE id = :id", parameters);
            driver.getCars().forEach(car -> carRepository.save(car));
        }
        return null;
    }

    @Override
    public List<Driver> findAll() {
        List<Driver> lista= getAllDriverCar();
        return lista;
    }

    @Override
    public Optional<Driver> findOne(Long id) {
        //List<Driver> lista= getAllDriverCar();
        List<Driver> drivers = getAllDriverCar().stream()
                .filter(e->e.getId() == id)
                .collect(Collectors.toList());
        if (drivers.size() == 1) {
            return Optional.of(drivers.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("delete from driver where id = :id", parameters);

        List<Car> cars = carRepository.findCarsByDriverId(id);
        for (Car car :cars){
            car.setDriverId(null);
            carRepository.save(car);
        }
    }
}
