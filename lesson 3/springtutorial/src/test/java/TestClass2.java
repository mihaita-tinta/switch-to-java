import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestClass2 {
    private static final Logger log = LoggerFactory.getLogger(TestClass2.class);

    @Test
    public void testApplicationContext_getBeanByType() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecondClass.class);

        MySpringBean applicationName = context.getBean(MySpringBean.class);

        assertEquals("APPLICATIONNAME", applicationName.value);
    }

    @Configuration
    public static class SecondClass {

        @Bean
        public String applicationName() {
            return "applicationName";
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
                public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
                    log.info("postProcessBeanFactory - {} beans found", configurableListableBeanFactory.getBeanDefinitionCount());
                    configurableListableBeanFactory.getBeanNamesIterator()
                            .forEachRemaining(
                                    beanName -> {
                                        log.info("postProcessBeanFactory - found {}", beanName);
                                        if (configurableListableBeanFactory.getBean(beanName).getClass().equals(MySpringBean.class)) {
                                            MySpringBean mySpringBean = configurableListableBeanFactory.getBean(beanName, MySpringBean.class);
                                            mySpringBean.value = mySpringBean.value.toUpperCase();
                                        }
                                    });
                }
            };
        }
    }

    public static class MySpringBean {
        String value;
    }
}
