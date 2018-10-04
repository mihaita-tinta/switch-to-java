import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class Part2_3_MethodsTest {

    @Test
    public void testMethod() {
        Entity a = new Entity();

        // Java is a pass by value language
        a.method("test");

        String[] args = new String[] {"a", "b"};
        System.out.println("method - args : " + args);
        a.method(args);
        System.out.println("method - args : " + Arrays.toString(args));

        // args was updated in the method call
        // usually a method should return the object that was changed instead of changing the input parameters!
        assertEquals(new String[] {"c", "b"}, args);
    }

    class Entity {

        public void method(String string) {
            System.out.println("method - string : " + string);
        }
        public void method(String[] strings) {
            // if you debug here, you can see the strings var id is different from the args id from line 14
            System.out.println("method - strings : " + strings);
            System.out.println("method - strings : " + Arrays.toString(strings));
            strings[0] = "c";
            System.out.println("method - strings : " + Arrays.toString(strings));
        }
    }


    @Test
    public void when_callOverloadedMethod_expect_javaUsesOneConversionOnly() {
        EntityOverloading a = new EntityOverloading();

        // Passing an int primitive forces Java to do more than one conversion
        // 1 -> is int -> promote it to long -> wrap it to Long
        // this will cause a compilation error.
//        a.method(1);

        // the one below works because true is converted to Boolean
        a.method(true);
    }

    class EntityOverloading {


        public void method(Long a) {

        }

        public void method(Boolean a) {

        }
    }

    @Test
    public void when_passMethodAsArgument_expect_methodIsCalledLater() {

        assertEquals(false, new LambdaEntity()
                .lambda(new Predicate() {

                    @Override
                    public boolean test(Object o) {
                        return false;
                    }

                }));

        assertEquals(false, new LambdaEntity()
                .lambda(arg -> {
                    return false;
                }));

        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));

        list.removeIf(string -> "a".equals(string));

        assertEquals(2, list.size());
    }

    class LambdaEntity {

        public boolean lambda(Predicate predicate ) {
            return predicate.test("lambdaEntity");
        }
    }

}
