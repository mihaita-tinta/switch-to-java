package com.ing.switchtojava.carpoolingapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.vesalainen.jaxb.gpx.GpxType;
import org.vesalainen.jaxb.gpx.WptType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RiderLocationService {

    private List<RiderCoordonates> coordonatesList;

    RiderLocationService() throws JAXBException, IOException {
        loadGpsCoordonates();
    }

    private int carPositionIndex = 0;
    public  RiderCoordonates getNextPoint(){
        RiderCoordonates riderCoordonates = coordonatesList.get(carPositionIndex);
        carPositionIndex++;

        return  riderCoordonates;
    }

    public String getNextPointAsJson() throws JsonProcessingException {
        RiderCoordonates riderCoordonates = coordonatesList.get(carPositionIndex);
        String jsonObject = serializeObject(riderCoordonates);
        carPositionIndex++;

        return jsonObject;
    }

    public SseEmitter track(Long id){
        SseEmitter emitter = new SseEmitter();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                ()->{
                    SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event();
                    RiderCoordonates riderCoordonates = coordonatesList.get(carPositionIndex);
                    String jsonObject = null;
                    try {
                        jsonObject = serializeObject(riderCoordonates);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    carPositionIndex++;
                    eventBuilder.data(jsonObject);
                    try {
                        emitter.send(eventBuilder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },0,1, TimeUnit.SECONDS
        );
        return emitter;
    }

    private GpxType loadGpsCoordonates() throws JAXBException, IOException {
        File file = new ClassPathResource("route0.gpx").getFile();
        JAXBContext jc =  JAXBContext.newInstance(GpxType.class);
        GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();

        mapData(route);
        return  route;
    }

    private void mapData(GpxType route){
        List<WptType> wptCoordonates =  route.getTrk().get(0)
                .getTrkseg()
                .get(0)
                .getTrkpt();
        coordonatesList = new ArrayList<RiderCoordonates>();

        wptCoordonates.stream().forEach(point -> {
            coordonatesList.add(new RiderCoordonates(point.getLon(), point.getLat()));
        });
    }

    private String serializeObject(RiderCoordonates coordonates) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(coordonates);
    }


}
