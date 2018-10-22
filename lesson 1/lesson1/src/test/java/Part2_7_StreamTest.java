import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Part2_7_StreamTest {


    @Test
    public void testForEach() {

            Stream.of(1, 2, 3, 4, 5)
                .forEach(System.out :: println);
    }
    @Test
    public void testFiltering() {
        List<Integer> list = Stream.of(1, 2, 3, 4, 5)
                .filter(i -> i > 3)
                .collect(Collectors.toList());

        assertEquals(2, list.size());
    }

    @Test
    public void testMapAndForEach() {

       IntStream.range(0, 100)
                .mapToObj(i -> Car.of("B" + i + "ERU"))
                .forEach(System.out :: println);
    }

    @Test
    public void testFlatMap() {

        List<Car> cars = Arrays.asList(Car.of("B01ERU", 2), Car.of("B40PEL", 3));

        List<String> passengers = cars.stream()
                .flatMap(car -> car.getPassengers().stream())
                .collect(Collectors.toList());

        assertEquals(5, passengers.size());
    }


    @Test
    public void testParallelStream() {
        IntStream.range(0, 100)
                .mapToObj(i -> Car.of("B" + i + "ERU"))
                .parallel()
                .forEach(car -> {
                    System.out.println( "car: " + car + ", thread: " + Thread.currentThread().getName());
                });

    }

    @Test
    public void testReduce() {
        int sum = 0;// FIXME compute the sum of first 100 integers [0, 100)

        sum = IntStream.range(0, 100).sum();

        assertEquals(4950, sum);


    }

    static class Car {
        private String number;

        private List<String> passengers;

        public List<String> getPassengers() {
            return passengers;
        }
        public String getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return number;
        }

        public static Car of(String number) {
            return of(number, 0);
        }
        public static Car of(String number, int passengers) {
            Car car = new Car();
            car.number = number;
            car.passengers = IntStream.range(0, passengers)
                    .mapToObj(String:: valueOf)
                    .collect(Collectors.toList());
            return car;
        }
    }
}
