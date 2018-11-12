package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Car;
import com.ing.switchtojava.carpoolingapi.domain.CarPosition;
import com.ing.switchtojava.carpoolingapi.repository.CarPositionRepository;
import com.ing.switchtojava.carpoolingapi.repository.DriverRepository;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
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
public class CarPositionService {

    private final CarPositionRepository carPositionRepository;
    private int position;

    public CarPositionService(CarPositionRepository carPositionRepository) {
        this.carPositionRepository = carPositionRepository;
    }

    public CarPosition save(CarPosition carPosition) {
        return carPositionRepository.save(carPosition);
    }

    static List<WptType> carPosition() throws IOException, JAXBException {
        File file = new ClassPathResource("test_route.gpx").getFile();
        JAXBContext jc = JAXBContext.newInstance(GpxType.class);

        GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();

        return route.getTrk().get(0)
                .getTrkseg()
                .get(0)
                .getTrkpt();
    }

    public CarPosition getNextPosition () throws IOException, JAXBException {
        WptType pos = carPosition().get(position++);
        return new CarPosition(pos.getLat(), pos.getLon());
    }


}
