import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class CarRepository {

    private JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:tcp://localhost:9092/testdb", "sa", "");

    public Car save(Car car) throws Exception {
        Connection con = cp.getConnection();

        PreparedStatement st = con.prepareStatement("INSERT INTO CAR (id, number, seats) VALUES(?,?,?)");
        st.setLong(1, car.getId());
        st.setString(2, car.getNumber());
        st.setInt(3, car.getSeats());
        int lines = st.executeUpdate();
        con.close();

        return car;
    }

    public Optional<Car> findOne(Long id) {
        return Optional.empty();
    }
}

