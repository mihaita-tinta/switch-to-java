package com.ing.switchtojava.carpoolingapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.exception.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.exception.RideNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.model.CarPosition;
import com.ing.switchtojava.carpoolingapi.rest.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.vesalainen.jaxb.gpx.GpxType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RideService {

    private final RideRepository rideRepository;

    private final PassengerRepository passengerRepository;

    private final ObjectMapper objectMapper;

    private final SseEmitter emitter;

    public RideService(RideRepository rideRepository, PassengerRepository passengerRepository, ObjectMapper objectMapper, SseEmitter emitter) {
        this.rideRepository = rideRepository;
        this.passengerRepository = passengerRepository;
        this.objectMapper = objectMapper;
        this.emitter = emitter;
    }

    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    public Ride findById(Long id) {
        return rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
    }

    public Ride save(Ride ride) {

        return rideRepository.save(ride);
    }

    public Ride join(Long passengerId, Long rideId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(PassengerNotFoundException::new);
        Ride ride = rideRepository.findById(rideId).orElseThrow(RideNotFoundException::new);
        ride.getPassengers().add(passenger);
        return rideRepository.save(ride);
    }

    public CarPosition loadCoordinatesFromFile(Long rideId) throws JAXBException, IOException {
        File file = new ClassPathResource("/routes/route0.gpx").getFile();
        JAXBContext jc = JAXBContext.newInstance(GpxType.class);
        GpxType route = ((JAXBElement<GpxType>) jc.createUnmarshaller().unmarshal(file)).getValue();

        CarPosition carPosition = new CarPosition();
        route.getTrk().get(0)
                .getTrkseg()
                .get(0)
                .getTrkpt()
                .stream().forEach(el -> {
            Position position = new Position(el.getLat(), el.getLon());
            carPosition.getPositions().add(position);
        });

        return carPosition;
    }

    public SseEmitter track(Long id) throws JAXBException, IOException {
        CarPosition carPosition = loadCoordinatesFromFile(id);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    try {
                        if (carPosition.hasNext()) {
                            SseEmitter.SseEventBuilder event = SseEmitter.event();
                            String dataPosition = objectMapper.writeValueAsString(carPosition.next());
                            event.data(dataPosition + "\n")
                                    .id(String.valueOf(System.currentTimeMillis()))
                                    .name("Raw position for " + id);
                            emitter.send(event);
                        }
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }

                }, 0, 1, TimeUnit.SECONDS
        );

        return emitter;
    }
}
