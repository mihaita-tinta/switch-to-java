import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Part2_0_ExceptionTest {

    @Test
    public void testException() {
        try {
            throw new Exception("oops");
        } catch (Exception e) {
            System.out.println("catched");
        }
    }
    @Test
    public void testFinallyNotCalled() {
        try {
            System.exit(0);
        } finally {
            System.out.println("should not be called");
        }
    }

    @Test
    public void testMultipleCatch() {
        try {
            throw new IllegalArgumentException("test");
        } catch (IllegalArgumentException e) {
            System.out.println("called 1");
        } catch (Exception moreGeneral) {
            System.out.println("should not be called");
        }

        // similar with
        try {
            throw new IOException("test");
        } catch (IllegalArgumentException|IOException e) {
            System.out.println("called 2");
        }
    }

    @Test
    public void test() {
        // errors, serious problems
        Throwable error = new Error();
        Error oom = new OutOfMemoryError();

        // check exceptions, either catch or throw them
        Throwable exception = new Exception();

        // unchecked exceptions
        Throwable runtimeException = new RuntimeException();
        RuntimeException illegalArgumentException = new RuntimeException();
    }

}
