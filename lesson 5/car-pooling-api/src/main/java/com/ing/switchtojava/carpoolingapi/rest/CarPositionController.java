package com.ing.switchtojava.carpoolingapi.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.CarPosition;
import com.ing.switchtojava.carpoolingapi.repository.CarRepository;
import com.ing.switchtojava.carpoolingapi.service.CarPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class CarPositionController {

    private static final Logger log = LoggerFactory.getLogger(CarPositionController.class);

    @Autowired
    CarPositionService service;

    @GetMapping("/rides/{id}/car-position/")
    public SseEmitter getCarPosition(@PathVariable Long id) throws IOException, JAXBException {
        SseEmitter emitter = new SseEmitter();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            CarPosition position = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                position = service.getNextPosition();
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data(mapper.writeValueAsString(position) + "\n")
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name("CarPosition for " + id);

                emitter.send(event);
            } catch (IOException e) {
                e.printStackTrace();
//                emitter.... s-a terminat cursa
            }
            catch (JAXBException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

        return emitter;
    }


}
