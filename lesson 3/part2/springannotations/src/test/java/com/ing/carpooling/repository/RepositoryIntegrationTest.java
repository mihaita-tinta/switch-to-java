package com.ing.carpooling.repository;

import com.ing.carpooling.config.DatabaseConfig;
import com.ing.carpooling.config.PropertiesConfig;
import com.ing.carpooling.config.RepositoryConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Before running the tests you need to startup the database
 * java -jar h2-1.4.196.jar -baseDir ~/tmp/h2dbs
 *
 * jdbc:h2:~/tmp/h2dbs/carpooling
 */
public class RepositoryIntegrationTest {


    static AnnotationConfigApplicationContext context;

    @BeforeClass
    public static void beforeClass() {
        context = new AnnotationConfigApplicationContext(PropertiesConfig.class, DatabaseConfig.class,
                                                        RepositoryConfig.class);
    }

    @AfterClass
    public static void afterClass() {
        context.close();
    }
}
