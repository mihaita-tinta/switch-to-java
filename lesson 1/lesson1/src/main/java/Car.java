import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car {
    private static final int MAXIMUM_NUMBER_OF_SEATS = 5;
    private boolean numberOfSeats;

    @Override
    public String toString() {
        return "Car{" +
                "numberOfSeats=" + numberOfSeats +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.print("finalize - call");
        super.finalize();
    }

    public static void main(String[] args) {
        Car car = new Car();
        List<Integer> myCars = new ArrayList<Integer>();
        Integer a = 1;
        System.out.print(myCars.contains(1));
    }
}
