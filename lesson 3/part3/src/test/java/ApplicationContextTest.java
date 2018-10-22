import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ApplicationContextTest {
    private static final Logger log = LoggerFactory.getLogger((ApplicationContextTest.class));

    @Test
    public void testApplicationContext_getBeanByName() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication.class);

        Assert.assertTrue(context instanceof BeanFactory);

        String applicationName = context.getBean("applicationName", String.class);

        Assert.assertEquals("springApplication", applicationName);
    }

    @Configuration
    public static class MyFirstSpringApplication {

        @Bean
        public String applicationName() {
            return "springApplication";
        }
    }


    @Test
    public void testApplicationContext_getBeanByType() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication2.class);

        String applicationName = context.getBean(String.class);
        Map<String, String> m = context.getBeansOfType(String.class);

        Assert.assertEquals("springApplication1", applicationName);
    }

    @Configuration
    public static class MyFirstSpringApplication2 {

        @Bean
        public String applicationName1() {
            return "springApplication1";
        }

        @Bean
        public String applicationName2() {
            return "springApplication2";
        }
    }

    @Test
    public void testApplicationContext_postProcessBeans() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication3.class);
        MySpringBean bean = context.getBean(MySpringBean.class);
        Assert.assertEquals("SPRINGAPPLICATION", bean.value);
    }

    @Configuration
    public static class MyFirstSpringApplication3 {

        @Bean
        public String applicationName() {
            return "springApplication";
        }

        @Bean
        public MySpringBean mySpringBean(String applicationName) {
            MySpringBean bean = new MySpringBean();
            bean.value = applicationName;
            return bean;
        }

        @Bean
        public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
            return new BeanFactoryPostProcessor() {
                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
                    log.info("postProcessBeanFactory - {} beans found", configurableListableBeanFactory.getBeanDefinitionCount());
                    configurableListableBeanFactory.getBeanNamesIterator()
                            .forEachRemaining(beanName -> log.info("postProcessBeanFactory - found {} ", beanName));

                    MySpringBean sb = configurableListableBeanFactory.getBean("mySpringBean", MySpringBean.class);
                    sb.value = sb.value.toUpperCase();

                }
            };
        }
    }

    static class MySpringBean {
         String value;
    }

    @Test
    public void testApplicationContext_applicationListener() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication4.class);
        InMemoryApplicationListener applicationListener = context.getBean(InMemoryApplicationListener.class);
        context.close();
        Assert.assertEquals(2, applicationListener.getEvents().size());
    }

    @Configuration
    public static class MyFirstSpringApplication4 {

        @Bean
        public InMemoryApplicationListener listener() {
            return new InMemoryApplicationListener();
        }
    }

    static class InMemoryApplicationListener implements ApplicationListener {

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

    @Test
    public void testApplicationContext_beanEvents() {
         AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication5.class);

         WiseBean wiseBean = context.getBean(WiseBean.class);

         Assert.assertEquals("afterConstructor", wiseBean.postCreatedValue);
    }

    @Configuration
    public static class MyFirstSpringApplication5 {

        @Bean
        public static WiseBean wiseBean() {
            return new WiseBean();
        }
    }

    static class WiseBean {
        String postCreatedValue;

        @PostConstruct
        public void postConstruct() {
            postCreatedValue = "afterConstructor";
        }

        @PreDestroy
        public void preDestroy() {
            postCreatedValue = null;
        }
    }

    @Test
    public void testApplicationContext_properties() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication8.class);

        BeanWithProperties configuration = context.getBean("configuration", BeanWithProperties.class);

        Assert.assertEquals("Disneyland", configuration.developer);
    }

    @Configuration
    @PropertySource("application.properties")
    public static class MyFirstSpringApplication8 {

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
