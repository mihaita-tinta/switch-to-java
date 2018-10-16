package serialization;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class ConverterTest {

    // TODO 1 - fix failing tests

    @Test
    public void when_convertString_expect_returnString() {
        Converter converter = new Converter();
        assertEquals("Abc", converter.convert("Abc", String.class));
    }

    @Test
    public void when_convertLong_expect_returnLong() {
        Converter converter = new Converter();
        assertEquals(Long.valueOf(1000), converter.convert("1000", Long.class));
    }

    @Test
    public void when_convertInt_expect_returnInt() {
        Converter converter = new Converter();
        assertEquals(10, converter.convert("10", int.class));
    }

    @Test
    public void when_convertNullToInt_expect_returnDefaultValueForInt() {
        Converter converter = new Converter();
        assertEquals(0, converter.convert(null, int.class));
    }

    @Test
    public void when_convertNullToBoolean_expect_returnDefaultValueForBoolean() {
        Converter converter = new Converter();
        assertEquals(false, converter.convert(null, boolean.class));
    }

    @Test
    public void when_convertNullToDouble_expect_returnDefaultValueForDouble() {
        Converter converter = new Converter();
        assertEquals(0.0, converter.convert(null, double.class));
    }

    @Test
    public void when_convertDouble_expect_returnDouble() {
        Converter converter = new Converter();
        assertEquals(1.2, converter.convert("1.2", double.class));
    }


    @Test
    public void when_convertLocalDate_expect_returnLocalDate() {
        Converter converter = new Converter();
        assertEquals(LocalDate.of(2018, 10, 11), converter.convert("2018-10-11", LocalDate.class));
    }

    @Test
    public void when_convertLocalDateTime_expect_returnLocalDateTime() {
        Converter converter = new Converter();
        assertEquals(LocalDateTime.of(2018, 10, 11, 13, 15,0, 0),
                    converter.convert("2018-10-11T13:15:0.0", LocalDate.class));
    }
}
