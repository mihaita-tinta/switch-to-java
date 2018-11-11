package com.ing.switchtojava.carpoolingapi;

import com.ing.switchtojava.carpoolingapi.service.TrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@SpringBootApplication
public class CarPoolingApiApplication {
    private static final Logger log = LoggerFactory.getLogger(CarPoolingApiApplication.class);


    public static void main(String[] args) {
        log.debug("main - start");
        SpringApplication.run(CarPoolingApiApplication.class, args);
    }

//    public static void  test() throws JAXBException, IOException {
//        TrackingService ts = new TrackingService();
//        System.out.println(ts.getPositions());
//    }
}
