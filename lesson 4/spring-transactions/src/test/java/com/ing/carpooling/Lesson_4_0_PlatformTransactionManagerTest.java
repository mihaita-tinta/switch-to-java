package com.ing.carpooling;

import com.ing.carpooling.domain.Location;
import com.ing.carpooling.repository.LocationRepository;
import com.ing.carpooling.repository.RepositoryIntegrationTest;
import com.ing.carpooling.service.LocationService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Lesson_4_0_PlatformTransactionManagerTest extends RepositoryIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(Lesson_4_0_PlatformTransactionManagerTest.class);


    PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
    LocationRepository repository = context.getBean(LocationRepository.class);
    TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);
    LocationService locationService = context.getBean(LocationService.class);

    @Test
    public void testPlatformTransactionManager() {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // explicitly setting the transaction name is something that can be done only programmatically
        def.setName("saveLocation");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            repository.save(getSomeLocation());
        } catch (IllegalStateException ex) {
            transactionManager.rollback(status);
        }
        repository.findAll().forEach(l -> log.info(l.getAddress()));

        transactionManager.commit(status);

        repository.findAll().forEach(l -> log.info(l.getAddress()));

    }

    @Test
    public void testRollback() {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // explicitly setting the transaction name is something that can be done only programmatically
        def.setName("saveLocationRollback");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            repository.save(getSomeLocation());
            throw new IllegalStateException("We better do a rollback");
        } catch (IllegalStateException ex) {
            repository.findAll().forEach(l -> log.info(l.getAddress()));
            transactionManager.rollback(status);
        }

        repository.findAll().forEach(l -> log.info(l.getAddress()));

    }

    @Test
    public void testTransactionTemplate() {


        transactionTemplate.execute(transactionStatus -> repository.save(getSomeLocation()));

        repository.findAll().forEach(l -> log.info(l.getAddress()));

    }

    @Test
    public void testTransactional() {

        Location saved = locationService.save(getSomeLocation());

        Assert.assertNotNull(saved.getId());
    }


    Random random = new Random();
    private Location getSomeLocation() {
        Location location = new Location();
        location.setLatitude(22);
        location.setLongitude(12.1321);
        location.setAddress("S-Park, A2, Poligrafiei " + random.nextInt(1000));
        location.setCity("Bucuresti");
        location.setState("Bucuresti");
        location.setZip("123-" +  + random.nextInt(1000));
        return location;
    }
}
