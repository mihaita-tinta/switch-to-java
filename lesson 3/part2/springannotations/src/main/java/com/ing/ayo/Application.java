package com.ing.ayo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Configuration
@ComponentScan
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    //The Java Virtual Machine exits when the only threads running are all daemon threads.
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start("Main");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
//        new ClassPathXmlApplicationContext("app-config.xml");
//        context.scan("com.ing.ayo");
//        context.refresh();
        context.registerShutdownHook();

        watch.stop();
        log.info("main - stop {}", watch.prettyPrint());

    }
}
