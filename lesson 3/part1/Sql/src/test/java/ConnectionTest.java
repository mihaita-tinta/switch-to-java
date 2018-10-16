import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;

public class ConnectionTest {

    String url = "jdbc:h2:tcp://localhost:9092/testdb";
    //Logger log = LoggerFactory.getLogger();

    @Test
    public void test_firstQuery() throws SQLException
    {
        try(Connection con = DriverManager.getConnection(url,"sa","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1+1")) {
                if(rs.next())
                {
                    System.out.println("test-select: {}" + rs.getInt(1));
                }
            }
    }

    @Test
    public void test_print_table() throws SQLException
    {
        try(Connection con = DriverManager.getConnection(url,"sa","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name FROM TEST")) {
            int rows = 0;
            while(rs.next())
            {
                rows++;
                System.out.println("test-select: {}" + con + " id:" + rs.getString(1) +
                        " name: " + rs.getString(2));
            }
            assertEquals(2,rows);
        }
    }

    @Test
    public void test_print_table_withPreparedStatement() throws SQLException
    {
        String name = "test1";
        try(Connection con = DriverManager.getConnection(url,"sa","")) {
            PreparedStatement st = con.prepareStatement("SELECT id, name FROM TEST where name= ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();

            int rows = 0;
            while (rs.next()) {
                rows++;
                System.out.println("test-select: {}" + con + " id:" + rs.getString(1) +
                        " name: " + rs.getString(2));
            }
            assertEquals(0, rows);
        }
    }
    @Test
    public  void test_connectionPool() throws SQLException
    {
        JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/test","sa","");
        for(int i=0; i <15; i++)
        {
            Connection con = cp.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT 1+1");
            if(rs.next())
            {
                System.out.println("test-connection " + con + " select " + rs.getInt(1));
            }
            con.close();
        }
        cp.dispose();
    }

    @Test
    public void test_insert() throws SQLException
    {
        String name = "test1";
        Integer id = 1;
        try(Connection con = DriverManager.getConnection(url,"sa","")) {
            PreparedStatement st = con.prepareStatement("INSERT INTO TEST (id, name) VALUES(?,?)");
            st.setInt(1, id);
            st.setString(2,name);
            int lines = st.executeUpdate();

            /*int rows = 0;
            while (rs.next()) {
                rows++;
                System.out.println("test-select: {}" + con + " id:" + rs.getString(1) +
                        " name: " + rs.getString(2));
            }*/
            assertEquals(1, lines);
        }
    }
}



