package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Passenger;
import com.ing.carpooling.domain.Ride;
import com.ing.carpooling.domain.RideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RideRequestRepository implements CrudRepository<RideRequest, Long> {
    private static final Logger log = LoggerFactory.getLogger(RideRequestRepository.class);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS RIDEREQUEST (\n"+
            "id INT NOT NULL auto_increment, \n" +
            "id_passenger INT NOT NULL, \n" +
            "id_ride INT NOT NULL , \n" +
            "status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELED') NOT NULL, \n" +
            ");";

    private final RowMapper<RideRequest> mapper = new RowMapper<RideRequest>() {
        @Override
        public RideRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            RideRequest rideRequest= new RideRequest();
            try{
                rideRequest.setId(rs.getLong("id"));
                Optional<Passenger> passenger = passengerRepository.findOne(rs.getLong("id_passenger"));
                if(passenger.isPresent()){
                    rideRequest.setPassenger(passenger.get());
                }

                Optional<Ride> ride = rideRepository.findOne(rs.getLong("id_ride"));
                if(ride.isPresent()){
                    rideRequest.setRide(ride.get());
                }

                rideRequest.setStatus(RideRequest.Status.valueOf(rs.getString("status")));
            }catch (SQLException e){
                throw new IllegalStateException("Could not map row to riderequest", e);
            }
            return  rideRequest;
        }
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private PassengerRepository passengerRepository;
    private RideRepository rideRepository;

    public RideRequestRepository(NamedParameterJdbcTemplate namedJdbcTemplate, RideRepository rideRepository, PassengerRepository passengerRepository) {

        this.namedJdbcTemplate = namedJdbcTemplate;
        this.rideRepository = rideRepository;
        this.passengerRepository = passengerRepository;
    }



    @Override
    public RideRequest save(RideRequest rideRequest) {
        if(rideRequest.getId() == null){
            KeyHolder holder = new GeneratedKeyHolder();
            System.out.println("save id ride "+rideRequest.getRide().getId()+"  ##");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id_passenger", rideRequest.getPassenger().getId())
                    .addValue("id_ride", rideRequest.getRide().getId())
                    .addValue("status",rideRequest.getStatus().toString());
            namedJdbcTemplate.update("insert into RIDEREQUEST (id_passenger, id_ride, status)"+
                    "values (:id_passenger, :id_ride, :status)",parameters,holder);
            rideRequest.setId(holder.getKey().longValue());
        }
        else{
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id",rideRequest.getId())
                    .addValue("id_passenger", rideRequest.getPassenger().getId())
                    .addValue("id_ride", rideRequest.getRide().getId())
                    .addValue("status",rideRequest.getStatus());
            namedJdbcTemplate.update("UPDATE riderequest SET id_passenger = :id_passenger, id_ride = :id_ride,\n" +
                    "status = :status WHERE id = :id", parameters);

        }
        return rideRequest;
    }

    @Override
    public List<RideRequest> findAll() {
        log.info("findAll");
        return namedJdbcTemplate.query("select * from riderequest", mapper);
    }

    @Override
    public Optional<RideRequest> findOne(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id);
        try {
            return Optional.of(
                    namedJdbcTemplate.queryForObject("select * from riderequest where id = :id", parameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("findOne - no riderequest found id: {}, ", id);
            return Optional.empty();
        }
    }

    public List<RideRequest> findRideRequestByRide(Long rideId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id_ride", rideId);
        log.info("Find passengers by ride");
        return namedJdbcTemplate.query("select * from riderequest where id_ride = :id_ride", parameters, mapper);
    }

    @Override
    public void delete(Long id) {
        log.info("delete - id: {}, ", id);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update("delete from riderequest where id = :id", parameters);

    }
}
