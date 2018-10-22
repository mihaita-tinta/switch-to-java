import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.annotation.PostConstruct;
import java.util.Map;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;




public class Lesson_3_0_ApplicationContextTest {
    private static final Logger log = LoggerFactory.getLogger(Lesson_3_0_ApplicationContextTest.class);




    @Test
    public void testApplicationContext_getBeanbyName(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication.class);
        assertTrue(context instanceof BeanFactory);
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


//    @Test
//    public void testApplicationContext_applicationListener(){
//            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication4.class);
//            InMemoryApplicationListener applicationListener = context.getBean(InMemoryApplicationListener.class);
//
//            assertEquals(2,applicationListener.getEvents().size());
//        }
//
//
//    @Configuration
//    public static class MyFirstSpringApplication{}







    @Test
    public void testApplicationContext_beanEvent(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication5.class);
        WiseBean wiseBean = context.getBean(WiseBean.class);
    }

    @Configuration
    public static class MyFirstSpringApplication5{

        @Bean
        public static WiseBean wiseBean(){
            return new WiseBean();
        }
    }

    static class WiseBean {
        String postCreatedValue;

        @PostConstruct
        public void postConstruct(){
            postCreatedValue = "afterConstructor";
        }


    }

    @Test
    public void testApplicationContext_properties(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyFirstSpringApplication8.class);

        BeanWithProperties configuration = context.getBean("configuration",BeanWithProperties.class);

        assertEquals("cata",configuration.developer);
    }

    @Configuration
    @PropertySource("application.properties")
    public static class MyFirstSpringApplication8{

        @Bean
        public BeanWithProperties configuration(){
            return new BeanWithProperties();
        }


    }

    static class BeanWithProperties{
        @Value("application.developer")
        String developer;
    }







//    static class InMemoryApplicationListener implements ApplicationListener{
//        private List<ApplicationEvent>
//    }

    static class MySpringBean{
        String value;
    }
}
