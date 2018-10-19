package com.ing.carpooling.repository;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DatabaseConfigTest extends RepositoryIntegrationTest {
    Logger log = LoggerFactory.getLogger(DatabaseConfigTest.class.getName());

    @Test
    public void test() throws Exception {

        DataSource ds = context.getBean(DataSource.class);

        try (Connection con = ds.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1+1")) {

            if (rs.next()) {
                log.info("test - select: {}", rs.getInt(1));
                assertEquals(2, rs.getInt(1));
            } else {
                fail();
            }

        }
    }
}
