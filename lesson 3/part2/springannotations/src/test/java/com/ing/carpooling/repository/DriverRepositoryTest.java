package com.ing.carpooling.repository;

import com.ing.carpooling.domain.Driver;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DriverRepositoryTest extends RepositoryIntegrationTest {
    public static final Logger log= LoggerFactory.getLogger(DriverRepository.class);

    DriverRepository repository = context.getBean(DriverRepository.class);

    @Test
    public void TestCrud()
    {
        Driver driver = new Driver();
        driver.setFirstName("dan");
        driver.setLastName("James");

    }

}
