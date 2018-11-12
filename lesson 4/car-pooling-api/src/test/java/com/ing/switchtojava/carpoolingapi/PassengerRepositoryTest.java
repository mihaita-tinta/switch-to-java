package com.ing.switchtojava.carpoolingapi;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PassengerRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PassengerRepositoryTest.class);

    @Autowired
    PassengerRepository repository;

    @Sql("/passenger.sql")
    @Test
    public void test1() {
        repository.deleteById(1L);

    }

    @Sql("/passenger.sql")
    @Test
    public void test2() {
        Passenger ana = repository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Can't go on without Ana"));

    }
}
