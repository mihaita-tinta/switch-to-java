package com.ing.carpooling.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<S, T> {

    /**
     * @param instance
     * @return the updated instance. Usually the database is generating an unique ID that will be set for this instance
     */
    public S save(S instance);

    /**
     * @return all instances from the database
     */
    public List<S> findAll();

    /**
     * Perform an lookup into the database based on the id
     * @param id
     * @return
     */
    public Optional<S> findOne(T id);

    /**
     * Delete a specific row from the database using it's ID
     * @param id
     */
    public void delete(T id);
}
