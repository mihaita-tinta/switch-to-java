package com.ing.switchtojava.carpoolingapi.repository;


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
public class DriverRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(DriverRepositoryTest.class);
    @Autowired
    DriverRepository repository;

    @Sql("/driver.sql")
    @Test
    public void test() {
        repository.findAll()
                .forEach(driver -> log.debug("driver: " + driver));
    }

}
