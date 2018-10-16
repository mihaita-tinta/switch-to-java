import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Part2_6_CollectionsTest {

    @Test
    public void test_collections() {
        List<ObjectWithNoEqualsImplemented> list = new ArrayList<>();
        ObjectWithNoEqualsImplemented first = new ObjectWithNoEqualsImplemented(1);
        list.add(first);
        ObjectWithNoEqualsImplemented second = new ObjectWithNoEqualsImplemented(2);
        list.add(second);

        list.remove(new ObjectWithNoEqualsImplemented(2));
        assertEquals(2, list.size()); // the remove operation didn't remove the second instance

        assertTrue(list.remove(second));
        assertEquals(1, list.size()); // the remove operation removed the second instance because it used the reference


    }

    @Test
    public void test_removeObjectWithEquals() {
        List<ObjectWithEquals> list = new ArrayList<>();
        ObjectWithEquals first = new ObjectWithEquals(1);
        list.add(first);
        ObjectWithEquals second = new ObjectWithEquals(2);
        list.add(second);

        list.remove(new ObjectWithEquals(2));
        assertEquals(1, list.size()); // TODO size should be 1 after removing the second element DONE

    }

    @Test
    public void testMap() {
        Map<ObjectWithEqualsAndHashCode, Integer> map = new HashMap<>();
        ObjectWithEqualsAndHashCode first = new ObjectWithEqualsAndHashCode(1);

        map.put(first, 1);
        assertEquals(1, map.get(first).intValue());
        assertEquals(1, map.get(new ObjectWithEqualsAndHashCode(1)).intValue()); // FIXME implement equals and hashcode to make this pass DONE

    }

    @Test
    public void testReadAboutCollections() {
        boolean iJustReadEverything = true;
        // FIXME you need to read more about collections https://www.tutorialspoint.com/java/java_collections.htm DONE
        // you should change this to true after you get more insights about what other collections are there :P
        assertTrue(iJustReadEverything);
    }

    class ObjectWithNoEqualsImplemented {

        private int a;
        public ObjectWithNoEqualsImplemented(int a) {
            this.a = a;
        }

    }
    class ObjectWithEquals {
        private int a;
        public ObjectWithEquals(int a) {
            this.a = a;
        }
        // FIXME you need to implement equals DONE

        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            return a == ((ObjectWithEquals) obj).a;
        }
    }

    class ObjectWithEqualsAndHashCode {
        private int a;
        public ObjectWithEqualsAndHashCode(int a) {
            this.a = a;
        }
        // FIXME you need to implement equals and hashcode DONE

        @Override
        public boolean equals(Object obj) {

            if(obj == null) return false;

            return a == ((ObjectWithEqualsAndHashCode) obj).a;
        }

        @Override
        public int hashCode() {
            return a%100;
        }
    }
}
