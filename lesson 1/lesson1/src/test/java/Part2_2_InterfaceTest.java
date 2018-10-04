import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Part2_2_InterfaceTest {

    @Test
    public void testInterfaceInstantiatedWithAnonymousClass() {
        A a = new A() {};
    }

    @Test
    public void testB() {
        B b = new C();
        b.b();
        // static method from interfaces can't be called directly from the instance
//        b.staticB();
        B.staticB();
    }

    interface A {

    }

    interface B {

        int b = 1; // assumed public static final
        void b(); // assume public

        static void staticB() {

        }
    }

    class C implements B {

        @Override
        public void b() {

        }
    }
}
