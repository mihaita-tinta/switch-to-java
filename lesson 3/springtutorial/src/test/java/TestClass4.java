import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestClass4 {
    private static final Logger log = LoggerFactory.getLogger(TestClass4.class);

    @Test
    public void testApplicationContext_getBeanByType() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MySpringClass4.class);

        InMemoryApplicationListener applicationListener = context.getBean(InMemoryApplicationListener.class);
        context.close();
        assertEquals(2, applicationListener.getEvents().size());
    }

    @Configuration
    public static class MySpringClass4 {

        @Bean
        public InMemoryApplicationListener listener() {
            return new InMemoryApplicationListener();
        }
    }

    public static class InMemoryApplicationListener implements ApplicationListener {
        private List<ApplicationEvent> events = new ArrayList<>();

        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            log.info("onApplicationEvent - {} event", applicationEvent);
            events.add(applicationEvent);
        }

        public List<ApplicationEvent> getEvents() {
            return Collections.unmodifiableList(events);
        }
    }
}
