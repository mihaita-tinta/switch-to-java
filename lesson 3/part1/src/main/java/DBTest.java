import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBTest {


    Logger log = LoggerFactory.getLogger(DBTest.class.getName());
    String url = "jdbc:h2:tcp://localhost:9092/testdb";

    @Test
    public void test_firstQuery() throws SQLException {
        Connection con = DriverManager.getConnection(this.url, "sa", "");

    }

    @Test
    public void test_connectionPool() throws SQLException {
        JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/test", "sta", "");

        for(int i = 0; i < 15; ++i) {
            Connection con = cp.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1+1");
            if (rs.next()) {
                this.log.info("test - select: connection: {}, select: {}", con, rs.getInt(1));
            }
        }

        cp.dispose();
    }
}
