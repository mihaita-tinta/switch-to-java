import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Test2 {
    Logger log = LoggerFactory.getLogger(TestClass.lesson3_0_ConnectionTest.class.getName());
    String url = "jdbc:h2:tcp://localhost:9092/testdb";

    @Test
    public void test_firstQuery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "sa", "");
              Statement st = con.createStatement();
              ResultSet rs = st.executeQuery("SELECT 1 + 1")) {

            if (rs.next()) {
                log.info("test - select: {}", rs.getInt(1));
            }
        }

    }

    @Test
    public void test_print_table() throws SQLException {
        String name = "test1";
        try (Connection con = DriverManager.getConnection(url, "sa", "")){
             PreparedStatement st = con.prepareStatement("SELECT id, name from TEST WHERE name = ?");
             st.setString(1, name);
             ResultSet rs = st.executeQuery();

             int rows = 0;
             while(rs.next()) {
                 rows++;
                 log.info("test - select: connection: {}, id={}, name={}", con, rs.getString(1), rs.getString(2));
             }
            Assert.assertEquals(0, rows);
        }

    }

    @Test
    public void test_connectionPool() throws SQLException {
        JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/test", "sa", ""); // numar limitat de conexiuni - da timeout

        for (int i = 0; i < 15; i++) {
            Connection con = cp.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1 + 1");

            if (rs.next()) {
                log.info("test - connection: {}, select: {}", con, rs.getInt(1));
            }
        }

        cp.dispose();

    }


    @Test
    public void test_insertQuery() throws SQLException {
        String name = "New World";
        try (Connection con = DriverManager.getConnection(url, "sa", "")) {
             Statement st = con.createStatement();
             int rs = st.executeUpdate("INSERT INTO TEST VALUES (3, 'New World');");

             log.info("test - insert: {}", rs);

             PreparedStatement st2 = con.prepareStatement("SELECT id, name from TEST WHERE name = ?");
             st2.setString(1, name);
             ResultSet rs2 = st2.executeQuery();

            int rows = 0;
            while(rs2.next()) {
                rows++;
                log.info("test - select: connection: {}, id={}, name={}", con, rs2.getString(1), rs2.getString(2));
            }
            Assert.assertEquals(1, rows);
        }

    }
}

