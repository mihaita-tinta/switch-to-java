package com.ing.switchtojava.carpoolingapi.repository;

import  com.ing.switchtojava.carpoolingapi.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

}
