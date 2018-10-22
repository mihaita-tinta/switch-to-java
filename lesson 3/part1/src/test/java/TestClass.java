import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class TestClass {


    public class lesson3_0_ConnectionTest {
        Logger log = LoggerFactory.getLogger(lesson3_0_ConnectionTest.class.getName());
        String url = "jdbc:h2:tcp://localhost:9092/testdb";

        @Test
        public void test_firstQuery() throws SQLException {
            try (Connection con = DriverManager.getConnection(url, "sa", "");
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT 1 + 1")) {

                if (rs.next()) {
                    log.info("test - select: ()", rs.getInt(1));
                }
            }

        }
    }
}