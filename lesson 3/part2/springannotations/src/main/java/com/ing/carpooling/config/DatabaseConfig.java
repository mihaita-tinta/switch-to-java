package com.ing.carpooling.config;

import com.ing.carpooling.domain.Location;
import com.ing.carpooling.repository.LocationRepository;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public DataSource dataSource(ApplicationProperties properties) throws Exception {
        Properties props = new Properties();
        props.setProperty("url", properties.getUrl());
        props.setProperty("username", properties.getUsername());
        props.setProperty("password", properties.getPassword());
        DataSource ds = BasicDataSourceFactory.createDataSource(props);
        return ds;
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        initSchema(jdbcTemplate);
        return jdbcTemplate;
    }

    private void initSchema(JdbcTemplate jdbcTemplate) {
        log.info("initSchema - start");
        jdbcTemplate.update(LocationRepository.CREATE_TABLE);

        // TODO 0 here you need to add your create table statements

        log.info("initSchema - completed");
    }


}
