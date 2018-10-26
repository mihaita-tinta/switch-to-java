package com.ing.switchtojava.carpoolingapi;


import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
