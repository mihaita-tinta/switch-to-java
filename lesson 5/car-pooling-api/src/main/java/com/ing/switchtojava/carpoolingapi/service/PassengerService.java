package com.ing.switchtojava.carpoolingapi.service;

import com.ing.switchtojava.carpoolingapi.domain.Passenger;
import com.ing.switchtojava.carpoolingapi.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private final PassengerRepository repository;

    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public List<Passenger> findAll() {
        return repository.findAll();
    }

    public Optional<Passenger> findById(Long id) {
        return repository.findById(id);
    }

    public Passenger save(Passenger passenger) {
        return repository.save(passenger);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
