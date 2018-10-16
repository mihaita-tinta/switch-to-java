public class App {

    public static void main(String[] args) throws Exception {
        Car car = new Car();
        car.setId(12L);
        car.setNumber("2");
        car.setSeats(4);


        CarRepository carRepository = new CarRepository();
        carRepository.save(car);

    }
}
