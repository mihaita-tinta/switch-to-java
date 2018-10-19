import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestClass5 {
    private static final Logger log = LoggerFactory.getLogger(TestClass5.class);

    @Test
    public void testApplicationContext_getBeanEvents() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MySpringClass5.class);

        WiseBean wiseBean = context.getBean(WiseBean.class);

        assertEquals("afterConstructor", wiseBean.postCreate);
    }

    @Configuration
    public static class MySpringClass5 {

        @Bean
        public WiseBean wiseBean() {
            return new WiseBean();
        }
    }

    public static class WiseBean implements InitializingBean {
        String postCreate;

        @Override
        public void afterPropertiesSet() throws Exception {
            postCreate = "afterConstructor";
        }
    }
}
