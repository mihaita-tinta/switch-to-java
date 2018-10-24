package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Driver;
import com.ing.carpooling.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DriverRepository implements CrudRepository<Driver, Long> {
    private static final Logger log = LoggerFactory.getLogger(DriverRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DRIVER ( \n" +
            "   id INT NOT NULL auto_increment, \n" +
            "   firstName VARCHAR(20) NOT NULL,\n" +
            "   secondName VARCHAR(20) NOT NULL,\n" +
            ");";

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private CarRepository carRepository;
//http://forum.spring.io/forum/spring-projects/data/jdbc/746924-resultsetextractor-for-one-to-many-and-join-column-query

    public List<Driver> getAllDriverCar() {
        String sql = "SELECT driver.id, firstName, secondName, car.id, number, seats FROM driver LEFT JOIN car ON car.diverId = driver.id";
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
                    driver.setFirstName(resultSet.getString("firstName"));
                    driver.setLastName(resultSet.getString("secondName"));
                    map.put(id,driver);
                }

                List<Car> carList = driver.getCars();
                if (carList == null) {
                    carList = new ArrayList<>();
                    driver.setCars(carList);
                }

                Car car =new Car();
                car.setId(resultSet.getLong("id"));
                car.setSeats(resultSet.getInt("seats"));
                car.setNumber(resultSet.getString("number"));
                car.setId(resultSet.getLong("driverId"));
                carList.add(car);
                driver.setCars(carList);

            }
            return new ArrayList<>(map.values());
            }
        }
//    private final RowMapper<Driver> mapper = new RowMapper<Driver>() {
//        @Override
//        public Driver mapRow(ResultSet resultSet, int i) {
//            Driver driver = new Driver();
//            try {
//                driver.setId(resultSet.getLong("id"));
//                driver.setFirstName(resultSet.getString("firstName"));
//                driver.setLastName(resultSet.getString("secondName"));
//                //driver.setCars(resultSet.getString("cars"));
//
//            } catch (SQLException e) {
//                throw new IllegalStateException("Could not map row to location", e);
//            }
//            return driver;
//        }
//    };



    public DriverRepository(NamedParameterJdbcTemplate namedJdbcTemplate, CarRepository carRepository) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.carRepository = carRepository;
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
