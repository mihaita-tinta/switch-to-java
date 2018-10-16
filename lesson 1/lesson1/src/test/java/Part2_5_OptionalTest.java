import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class Part2_5_OptionalTest {

    @Test
    public void test() {

        Optional<Integer> a = Optional.of(1);

        assertEquals(true, a.isPresent());
        assertEquals(1, a.get().intValue());

        assertFalse(Optional.ofNullable(null).isPresent());

        Integer b = null;
        Optional.ofNullable(b)
                .ifPresent(value -> {
                    System.out.println("This is called only when present");
                    fail();
                });

        Optional.ofNullable(null)
                .orElseGet(() -> {
//                    Supplier like any other functional interface can be implemented with lambda
                    System.out.println("Supplier is a functional interface that can provide values. For us return the 1 value");
                    return 1;
                });

    }


    @Test
    public void when_oneOfTheParametersIsNull_expect_calculatorAddsADefaultValue0() {
        SmartCalculator calculator = new SmartCalculator();

        assertEquals(3, calculator.add(Optional.of(3), Optional.empty()));
    }


    class SmartCalculator {

        public int add(Optional<Integer> a, Optional<Integer> b) {
            // FIXME make SmartCalculator great again
            return 0;
        }
    }
}
