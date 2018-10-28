package com.ing.carpooling.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class Repository {

    protected final NamedParameterJdbcTemplate namedJdbcTemplate;

    public Repository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
}
