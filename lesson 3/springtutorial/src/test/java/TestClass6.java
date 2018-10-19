import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertEquals;

public class TestClass6 {
    private static final Logger log = LoggerFactory.getLogger(TestClass6.class);

    @Test
    public void testApplicationContext_getBeanEvents() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MySpringClass8.class);

        BeanWithProperties config = context.getBean("configuration", BeanWithProperties.class);

        assertEquals("Sandu", config.developer);
    }

    @Configuration
    public static class MySpringClass8 {

        @Bean
        public BeanWithProperties configuration() {
            return new BeanWithProperties();
        }
    }

    static class BeanWithProperties {

        @Value("${application.developer}")
        String developer;
    }
}
