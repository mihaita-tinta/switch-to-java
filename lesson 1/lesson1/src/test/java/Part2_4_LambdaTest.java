import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Part2_4_LambdaTest {


    @Test
    public void testSimpleLambda() {

        Calculator addition = ((a, b) -> a + b);

        assertEquals(2, addition.operate(1, 1));

        // other ways of declaring a lambda
        addition = ((a, b) -> {
                                return a + b;
                    });


        // before lambda you had to do this:
        addition = new Calculator() {
            @Override
            public int operate(int a, int b) {
                return a + b;
            }
        };

        Calculator multiplication = ((a, b) -> a * b);
        assertEquals(1, multiplication.operate(1, 1));

    }

    @Test
    public void testNotReallyACalculator() {
        NotReallyACalculator dummy = new NotReallyACalculator();

        Calculator addition = ((a, b) -> a + b);
        Calculator multiplication = ((a, b) -> a * b);

        assertEquals(3, dummy.operate(1, 2, addition));
        assertEquals(4, dummy.operate(2, 2, addition));
        assertEquals(200, dummy.operate(2, 100, multiplication));
    }

    @Test
    public void testClassMethodReference() {
        NotReallyACalculator dummy = new NotReallyACalculator();

        // sometimes it's easier to just reference the method instead of creating a lambda expression
        assertEquals(4, dummy.operate(2, 2, StaticCalculator::add));
    }

    @Test
    public void testInstanceMethodReference() {
        NotReallyACalculator dummy = new NotReallyACalculator();

        AddCalculator calculator = new AddCalculator();

        // you can also pass the instance method
        assertEquals(4, dummy.operate(2, 2, calculator :: add));
    }

    interface Calculator {
        int operate(int a, int b);
    }

    class NotReallyACalculator {

        public int operate(int a, int b, Calculator calculator) {
            return calculator.operate(a, b);
        }
    }

    static class StaticCalculator {
        /**
         * @param a first operand
         * @param b second operand
         * @return a + b
         */
        static int add(int a, int b) {
            return a + b;
        }
    }
    class AddCalculator {

        int add(int a, int b) {
            return a + b;
        }
    }

}
