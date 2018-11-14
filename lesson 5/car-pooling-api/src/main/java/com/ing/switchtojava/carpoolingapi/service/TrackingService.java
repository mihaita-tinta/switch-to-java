package com.ing.switchtojava.carpoolingapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.vesalainen.jaxb.gpx.GpxType;
import org.vesalainen.jaxb.gpx.WptType;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TrackingService {

    private final ObjectMapper objectMapper;
    public  TrackingService(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    public List<Position> getPositions() throws JAXBException, IOException {

        File file = new ClassPathResource("/route0.gpx").getFile();
        JAXBContext jc = JAXBContext.newInstance(GpxType.class);

        GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();

        List<Position> positions = new ArrayList<Position>();
        route.getTrk()
                .get(0)
                .getTrkseg()
                .get(0)
                .getTrkpt()
                .stream()
                .forEach(a->{
                    Position position = new Position(a.getLat(), a.getLon());
                    positions.add(position);
                });
        return positions;
    }

    public SseEmitter trackPosition ( Long id) throws JAXBException, IOException{
        SseEmitter emitter = new SseEmitter();
        Iterator<Position> iter = getPositions().iterator();

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(()-> {
                    try {
                        if(iter.hasNext()){
                            SseEmitter.SseEventBuilder event = SseEmitter.event()
//                                .data(writeAsString(iter.next()))
                                .data(objectMapper.writeValueAsString(iter.next()))
                                .id(String.valueOf(System.currentTimeMillis()))
                                .name("Raw position for id " + id);
                            emitter.send(event);
                        //    iter.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },0,1, TimeUnit.SECONDS);

        return emitter;
    }

    public Flux<Position> generateEvents(Long id) throws JAXBException, IOException {
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        Flux<Position> positions = Flux.fromStream(getPositions().stream());
        return Flux.zip(positions, durationFlux).map(Tuple2::getT1);

    }





    public String writeAsString(Position position) {
        try {
            return new ObjectMapper().writeValueAsString(position)+"\n\n";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }
    }


}
