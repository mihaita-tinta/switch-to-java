import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.*;

public class TestClass {
    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    @Test
    public void testApplicationContext_getBeanByName() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApp.class);

        assertTrue(context instanceof BeanFactory);

        String applicationName = context.getBean("applicationName", String.class);

        assertEquals("applicationName", applicationName);
    }

    @Configuration
    public static class MyFirstSpringApp {

        @Bean
        public String applicationName() {
            return "applicationName";
        }
    }
}
