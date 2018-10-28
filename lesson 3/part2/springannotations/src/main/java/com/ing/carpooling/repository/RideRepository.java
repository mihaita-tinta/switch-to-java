package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Car;
import com.ing.carpooling.domain.Location;
import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.swing.plaf.basic.BasicTreeUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class RideRepository implements CrudRepository<Ride, Long> {
    private  static final Logger log = LoggerFactory.getLogger(PassengerRepository.class);

//    private Long id;
//    private Location from;
//    private Location to;
//    private ZonedDateTime when;
//    private Car car;
//    private Ride.Status status;
//    private List<Passenger> passengers;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDE (\n"+
            "id INT NOT NULL auto_increment, \n" +
            "id_locationFrom INT NOT NULL, \n" +
            "id_locationTo INT NOT NULL , \n" +
            "when TIMESTAMP WITH TIME ZONE , \n" +
            "id_car INT NOT NULL , \n" +
            "status VARCHAR(50) NOT NULL, \n" +
            ");";
    //need to create other table for enumerations
    //https://stackoverflow.com/questions/40879835/inserting-enum-values-to-table-using-hibernate
    //https://softwareengineering.stackexchange.com/questions/305148/why-would-you-store-an-enum-in-db

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private PassengerRepository passengerRepository;
    private LocationRepository locationRepository;
    private CarRepository carRepository;

    public RideRepository(NamedParameterJdbcTemplate namedJdbcTemplate, PassengerRepository passengerRepository,
                          LocationRepository locationRepository, CarRepository carRepository) {

        this.namedJdbcTemplate = namedJdbcTemplate;
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.passengerRepository = passengerRepository;
    }

    private final RowMapper<Ride>  mapper = new RowMapper<Ride>() {
        @Override
        public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ride ride = new Ride();
            try{
                ride.setId(rs.getLong("id"));
                Optional<Location> locationFrom = locationRepository.findOne(rs.getLong("id_locationFrom"));
                if(locationFrom.isPresent()){
                    ride.setFrom(locationFrom.get());
                }
                Optional<Location> locationTo = locationRepository.findOne(rs.getLong("id_locationTo"));
                if(locationTo.isPresent()){
                    ride.setTo(locationTo.get());
                }

                ride.setWhen(ZonedDateTime.parse(rs.getString("when")));

                Optional<Car> car = carRepository.findOne(rs.getLong("id_car"));
                if(car.isPresent()){
                    ride.setCar(car.get());
                }

                ride.setStatus(Ride.Status.valueOf(rs.getString("status")));
            }catch (SQLException e){
                throw new IllegalStateException("Could not map row to ride", e);
            }
            return ride;
        }
    };

    @Override
    public Ride save(Ride ride) {
        locationRepository.save(ride.getFrom());
        locationRepository.save(ride.getTo());
        carRepository.save(ride.getCar());
        ride.getPassengers().forEach(passenger -> passengerRepository.save(passenger));

        if(ride.getId() == null){
            KeyHolder holder =new GeneratedKeyHolder();

            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id_locationFrom",ride.getFrom().getId())
                    .addValue("id_locationTo",ride.getTo().getId())
                    .addValue("when",ride.getWhen())
                    .addValue("id_car",ride.getCar().getId())
                    .addValue("status",ride.getStatus().toString()); //SasASasdsada

            System.out.println("Sunt in save iar status este "+ride.getStatus());
            namedJdbcTemplate.update("insert into ride (id_locationFrom, id_locationTo, when, id_car, status) \n" +
                    "values (:id_locationFrom, :id_locationTo, :when, :id_car, :status)", parameters, holder);
            ride.setId(holder.getKey().longValue());


        }
        else{
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id",ride.getId())
                    .addValue("id_locationFrom",ride.getFrom().getId())
                    .addValue("id_locationTo",ride.getTo().getId())
                    .addValue("when",ride.getWhen())
                    .addValue("id_car",ride.getCar().getId())
                    .addValue("status",ride.getStatus());

            namedJdbcTemplate.update("UPDATE ride SET id_locationFrom= :id_locationFrom, id_locationTo= :id_locationTo,\n" +
                    "when=:when, id_car=:id_car, status=:status WHERE id = :id", parameters );

        }
        return null;
    }

    @Override
    public List<Ride> findAll() {
        log.info("findAll");
        List<Ride> rides =  namedJdbcTemplate.query("select * from riderequest", mapper);
        rides.stream().forEach(ride ->{
            ride.setPassengers(passengerRepository.findPassengerByRide(ride.getId()));
        });
        return null;
    }

    @Override
    public Optional<Ride> findOne(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id);
        try {
            Optional<Ride> ride=  Optional.of(
                    namedJdbcTemplate.queryForObject("select * from ride where id = :id", parameters, mapper));
            Ride r = ride.get();
            r.setPassengers(passengerRepository.findPassengerByRide(r.getId()));
            return Optional.of(r);

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
        namedJdbcTemplate.update("delete from ride where id = :id", parameters);
    }
}
