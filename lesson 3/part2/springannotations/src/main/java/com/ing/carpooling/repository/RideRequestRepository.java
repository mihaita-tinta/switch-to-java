package com.ing.carpooling.repository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ing.carpooling.domain.RideRequest;

public class RideRequestRepository extends Repository implements CrudRepository<RideRequest, Long> {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR ( \n" +
        "   ID INT NOT NULL auto_increment, \n" +
        "   NUMBER VARCHAR(50) NOT NULL,\n" +
        "   SEATS INT NOT NULL, \n" +
        ");";
    private static final Logger log = LoggerFactory.getLogger(LocationRepository.class);

    public RideRequestRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        super(namedJdbcTemplate);
    }

    @Override
    public RideRequest save(RideRequest instance) {
        // TODO 0 implement RideRequest crud
        return null;
    }

    @Override
    public List<RideRequest> findAll() {
        // TODO 0 implement RideRequest crud
        return null;
    }

    @Override
    public Optional<RideRequest> findOne(Long id) {
        // TODO 0 implement RideRequest crud
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO 0 implement RideRequest crud

    }
}
