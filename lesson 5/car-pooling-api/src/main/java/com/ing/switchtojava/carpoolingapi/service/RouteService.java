package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.vesalainen.jaxb.gpx.GpxType;
import org.vesalainen.jaxb.gpx.WptType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteService.class);
    private static int index        = 1;

    public GpxType getRoutes() {
        try {
            File file = new ClassPathResource("/routes.gpx").getFile();
            JAXBContext jc = JAXBContext.newInstance(GpxType.class);
            GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();
            return route;
        } catch (IOException e) {
            log.info(e.getMessage());
            System.out.println("Error");
        } catch (JAXBException e) {
            log.info(e.getMessage());
            System.out.println("Error");
        }
        return new GpxType();
    }

    private List<WptType> getTracks() {
        return this.getRoutes().getTrk().get(0).getTrkseg().get(0).getTrkpt();
    }

    public Position getNextPosition() {
        List<WptType> l = this.getTracks();
        Position p      = new Position();
        if (l.size()-1 > index) {
            p = new Position(l.get(index).getLat(), l.get(index).getLon());
            index++;
        }
        return p;
    }

    public boolean hasNext() {
        return this.getTracks().size()-1 > index;
    }
}