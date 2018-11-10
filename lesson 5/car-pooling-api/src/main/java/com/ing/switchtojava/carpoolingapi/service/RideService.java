package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.domain.Ride;
import com.ing.switchtojava.carpoolingapi.exception.PassengerNotFoundException;
import com.ing.switchtojava.carpoolingapi.exception.RideNotFoundException;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import com.ing.switchtojava.carpoolingapi.repository.RideRepository;
import com.ing.switchtojava.carpoolingapi.rest.model.CarPosition;
import com.ing.switchtojava.carpoolingapi.rest.model.Position;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.vesalainen.jaxb.gpx.GpxType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;

    private final PassengerRepository passengerRepository;

    public RideService(RideRepository rideRepository, PassengerRepository passengerRepository) {
        this.rideRepository = rideRepository;
        this.passengerRepository = passengerRepository;
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

    public CarPosition getCarPositionsFromFile(Long rideId) throws JAXBException, IOException {
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
}
