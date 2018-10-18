import java.sql.*;
import java.util.Optional;

public class CarRepository {

    private static Connection con;
    static {
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/testdb", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Car save(Car car) throws SQLException{
        PreparedStatement st = con.prepareStatement("INSERT INTO CAR VALUES (?, ?, ?)");
        car.setId(1L);
        st.setLong(1, 1L);
        st.setString(2, "DJ 31 ASV");
        st.setInt(3, 5);
        int rs = st.executeUpdate();
        System.out.println(rs);

        return car;
    }

    public Optional<Car> findOne(Long id) {
        return Optional.empty();
    }
}
