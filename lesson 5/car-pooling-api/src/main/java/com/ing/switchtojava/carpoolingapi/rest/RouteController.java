package com.ing.switchtojava.carpoolingapi.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.carpoolingapi.domain.Position;
import com.ing.switchtojava.carpoolingapi.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.persistence.Tuple;
import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@RestController
@RequestMapping("/routes/")
public class RouteController {

    private static final Logger log = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    RouteService routeService;


    @GetMapping("sse")
    public SseEmitter getPosition() {
        ObjectMapper mapper = new ObjectMapper();
        SseEmitter emitter  = new SseEmitter();
        Executors.newSingleThreadScheduledExecutor()
                 .scheduleAtFixedRate(() -> {
                    Position position = new Position();
                    try {
                        position                            = routeService.getNextPosition();
                        String positionAsJson               = mapper.writeValueAsString(position);
                        SseEmitter.SseEventBuilder event    = SseEmitter.event();
                        event.data(positionAsJson + "\n\n")
                                .id(String.valueOf(System.currentTimeMillis()))
                                .name("Position for " + position.hashCode());
                        emitter.send(event);
                    } catch (Exception e) {
                        throw new ServerErrorException();
                    }
                }, 0L, 1L, TimeUnit.SECONDS);

        return emitter;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getPositions() {
        ObjectMapper mapper = new ObjectMapper();
        return Flux.interval(Duration.ofSeconds(1))
                   .map(data -> {
                        String positionAsJson   = new String("");
                        Position position       = new Position();
                        try {
                            position        = routeService.getNextPosition();
                            positionAsJson  = mapper.writeValueAsString(position);
                        } catch (Exception e) {
                            throw new ServerErrorException();
                        }
                        return ServerSentEvent.<String>builder()
                                                .data(positionAsJson + "\n\n")
                                                .id(String.valueOf(System.currentTimeMillis()))
                                                .event("Position for " + position.hashCode())
                                                .build();
                        }
                    );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleServerError(ServerErrorException e) {
        log.warn("server error - " + e);
    }
}