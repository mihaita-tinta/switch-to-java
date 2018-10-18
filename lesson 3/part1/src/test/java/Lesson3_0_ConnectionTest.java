import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static org.junit.Assert.assertEquals;

public class Lesson3_0_ConnectionTest {
    Logger log = LoggerFactory.getLogger(Lesson3_0_ConnectionTest.class.getName());
    String url = "jdbc:h2:tcp://localhost:9092/testdb";

    @Test
    public void test_firstQuery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "sa", "");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1+1")) {
            if (rs.next()) {
                log.info("test - select {}", rs.getInt(1));
            }
        }
    }

    @Test
    public void test_secondQuery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "sa", "")) {
             PreparedStatement st = con.prepareStatement("SELECT id, name FROM TEST WHERE name = ?");
             st.setString(1, "World");
             ResultSet rs = st.executeQuery();
             int rows = 0;
             while (rs.next()) {
                rows++;
                log.info("test - select conection: {}, id={}, name={}", con, rs.getString(1), rs.getString(2));
             }

             assertEquals(2, rows);
        }
    }

    @Test
    public void test_connectionPool() throws SQLException {
        JdbcConnectionPool cp = JdbcConnectionPool.create(url, "sa", "");

        for (int i = 0; i < 15; i++) {
            Connection con = cp.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1+1");
            if (rs.next()) {
                log.info("test - connection: {}, select {}", con, rs.getInt(1));
            }
        }
        cp.dispose();
    }

    @Test
    public void test_insertQuery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "sa", "")) {
             Statement st = con.createStatement();
             int result = st.executeUpdate("INSERT INTO TEST VALUES (3, 'Test')");
             log.info("result {}:", result);
        }
    }

    @Test
    public void test_carInsert() throws SQLException {
        CarRepository carRepository = new CarRepository();
        carRepository.save(new Car());
    }
}
