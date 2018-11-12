package com.ing.switchtojava.carpoolingapi.repository;


import com.ing.switchtojava.carpoolingapi.domain.Car;
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
public class CarRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(CarRepositoryTest.class);
    @Autowired
    CarRepository repository;

    @Sql("/car.sql")
    @Test
    public void test() {
        Car car = repository.findById(1L).get();

        Assert.assertNotNull(car);
    }

    @Sql("/car.sql")
    @Test
    public void testSpec() {

        repository.findAll(new CarSpec())
        .forEach(car -> log.debug("car {}", car));

    }

    static class CarSpec implements Specification<Car> {
        @Override
        public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

            return criteriaBuilder.equal(root.get("number"), "IL11ABC");
        }
    }
}
