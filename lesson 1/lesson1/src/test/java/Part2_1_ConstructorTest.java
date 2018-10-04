import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class Part2_1_ConstructorTest {

    @Test
    public void testInitialization() {
        C c = new C();
        System.out.println("testInitialization c " + c.method("a"));
    }

    @Test
    public void testOverridenMethodIsUsedInParent() throws Exception { // notice we had to throw the error or catch it
        D d = new C();
        System.out.println("testInitialization d " + d.method("a"));
    }

    @Test
    public void testHideStaticMethod() {
        C c = new C();
        D d = c;

        assertEquals(false, C.staticMethod());
        assertEquals(false, c.staticMethod());
        assertEquals(true, d.staticMethod());
        assertEquals(true, D.staticMethod());

    }

    static class C extends D { // this is the subclass/child/descendent class
        static int a = 1;
        static {
            System.out.println("static block C " + a);
        }
        {
            a = 2;
            System.out.println("block C " + a);
        }
        public C() {
//             super(); the compiler adds a super() call in the constructor automatically
            System.out.println("constructor C " + a);
        }

        @Override
        public Integer method(String a) { // we can increase the visibility, return a covariant type and not throw an Exception
            return 2;
        }

//        @Override we can't override it, but we can still hide it
        static boolean staticMethod() {
            return false;
        }

    }

    static abstract class D { // this is the parent class/ancestor
        static {
            System.out.println("static block D ");
        }
        {
            System.out.println("block D ");
        }
        public D() {
            System.out.println("constructor D ");
        }

        Number method(String a) throws Exception {
            return 1;
        }

        static boolean staticMethod() {
            return true;
        }
    }

}
