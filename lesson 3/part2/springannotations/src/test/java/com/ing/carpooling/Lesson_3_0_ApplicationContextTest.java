package com.ing.carpooling;

import junit.framework.TestCase;
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


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Lesson_3_0_ApplicationContextTest {
    private static final Logger log = LoggerFactory.getLogger(Lesson_3_0_ApplicationContextTest.class);

    @Test
    public void testApplicationContext_getBeanbyName(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication.class);
        TestCase.assertTrue(context instanceof BeanFactory);
        String applicationName = context.getBean("applicationName",String.class);
        assertEquals("applicationName", applicationName);
    }

    @Configuration
    public static class MyFirstSpringApplication{
        @Bean
        public String applicationName(){
            return "applicationName";
        }
    }



    @Test
    public void testApplicationContext_getBeanByType(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication2.class);
        // String applicationName = context.getBean(String.class);
        Map<String,String> applicationName = context.getBeansOfType(String.class);
        //System.out.println(applicationName);
        assertEquals("springApplication1",applicationName.get("applicationName1"));

        String applicationName2 = context.getBean("applicationName2",String.class);
        assertEquals("springApplication2",applicationName2);
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
    public void testApplicationContext_postProcessBeans(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication3.class);
        MySpringBean bean = context.getBean(MySpringBean.class);
        assertEquals("SPRINGAPPLICATION",bean.value);
    }

    @Configuration
    public static class MyFirstSpringApplication3 {

        @Bean
        public String applicationName() {
            return "springapplication";
        }

        @Bean
        public MySpringBean mySpringBean() {
            MySpringBean bean = new MySpringBean();
            bean.value = applicationName();
            return bean;
        }

        @Bean
        public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
            return new BeanFactoryPostProcessor() {
                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
                    log.info("postProcessFactory - {} beans found", configurableListableBeanFactory.getBeanDefinitionCount());
                    configurableListableBeanFactory.getBeanNamesIterator()
                            .forEachRemaining(beanName -> log.info("postProcessBeanFactory - found {}", beanName));
                    MySpringBean b = configurableListableBeanFactory.getBean("mySpringBean", MySpringBean.class);
                    b.value = b.value.toUpperCase();
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
        // TODO 4 what can we call on the context instance so that we receive also the shutdown event;
        context.close();
        assertEquals(2, applicationListener.getEvents().size());
        // TODO 4 how can we tell the context to be sure it closes when the jvm stops?

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

        // TODO 0 what is the correct value?
        assertEquals("afterConstructor", wiseBean.postCreatedValue);

    }

    @Configuration
    public static class MyFirstSpringApplication5 {

        @Bean
        public static WiseBean wiseBean() {
            return new WiseBean();
        }
    }

    static class WiseBean {//implements InitializingBean, DisposableBean {
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
    public void testApplicationContext_programaticallyCreateBeans() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication6.class);

        DynamicBean a = context.getBean("dynamicBean1", DynamicBean.class);
        // TODO 0 what is the correct value?
        assertEquals(1, a.index);

    }

    @Configuration
    public static class MyFirstSpringApplication6 {

        @Bean
        public BeanDefinitionRegistryPostProcessor definitionRegistryPostProcessor() {
            return new BeanDefinitionRegistryPostProcessor() {
                @Override
                public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
                    //TODO we need to create a bean definition to fix the test
                    BeanDefinitionBuilder builder = null;

                    beanDefinitionRegistry.registerBeanDefinition("dynamicBean1", builder.getBeanDefinition());
                }

                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

                }
            };
        }
    }

    static class DynamicBean{
        int index;

        public void setIndex(int index) {
            this.index = index;
        }
    }

    @Test
    public void testApplicationContext_programaticallyCreateBeans2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication7.class);

        DynamicBean a = context.getBean("dynamicBean1", DynamicBean.class);
        // TODO 0 what is the correct value?
        assertEquals(100, a.index);

    }

    @Configuration
    public static class MyFirstSpringApplication7 implements BeanFactoryAware {

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;

            if (((DefaultListableBeanFactory) beanFactory)
                        .getBeansOfType(DynamicBean.class)
                        .isEmpty()) {
                // FIXME make the test pass
                ((DefaultListableBeanFactory) beanFactory).registerSingleton("adasd", new WiseBean());
            }

        }
    }

    @Test
    public void testApplicationContext_properties() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication8.class);

        BeanWithProperties configuration = context.getBean("configuration", BeanWithProperties.class);

        // TODO 0 what is the correct value?
        assertEquals("Mihaita", configuration.developer);

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

        @Value("${application.developerIng}")
        String developer;
    }


    @Test
    public void testApplicationContext_profiles() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.addActiveProfile("prod");
        context.setEnvironment(environment);

        context.register(
                MyFirstSpringApplication9.class,
                MyFirstSpringApplication10.class);
        context.refresh();
        // TODO 0 what is the correct value?
        assertEquals("dev", context.getBean("profileDependentBean"));

    }

    @Configuration
    @Profile("dev")
    public static class MyFirstSpringApplication9 {

        @Bean
        public String profileDependentBean() {
            return "dev";
        }
    }
    @Configuration
    @Profile("prod")
    public static class MyFirstSpringApplication10 {

        @Bean
        public String profileDependentBean() {
            return "prod";
        }
    }
}
