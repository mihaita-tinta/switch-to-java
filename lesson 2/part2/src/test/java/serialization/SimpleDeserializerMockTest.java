package serialization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class SimpleDeserializerMockTest {

    @InjectMocks
    SimpleDeserializer deserializer;

    @Mock
    Converter converter;

    @Mock
    KeyValueExtractor extractor;

    @Mock
    Map<String, String> keyValues;


    @Test
    public void when_mockConfigured_expect_itReturnsCorrectValue() {
        Mockito.when(keyValues.get("abc")).thenReturn("123");

        assertEquals("123", keyValues.get("abc"));
        assertNull(keyValues.get("ABC"));

        Mockito.verify(keyValues, Mockito.times(2)).get(Matchers.anyString());
    }

    @Test
    public void when_usingMocks_expect_liveIsEasier() {

        Mockito.when(keyValues.get("setId")).thenReturn("10");
        Mockito.when(converter.convert("10", Long.class)).thenReturn(10L);

        //NU stiu daca asa trebuia facuta...chestia aia cu map??

        Map<String, String> map = new HashMap<>();
        map.put("id","10");
//        Mockito.when(extractor.extractKeyValues("someInput")).thenReturn(keyValues);
        Mockito.when(extractor.extractKeyValues("someInput")).thenReturn(map);
        OnePropertyClass instance = deserializer.deserialize("someInput", OnePropertyClass.class);


        assertEquals(new Long(10), instance.getId());
    }

    @Test
    public void when_TwoPropertyClass_expect_bothPropertiesAreSet() {

        //TODO 0 add mock behavior so that both properties are set
        //NU stiu daca asa trebuia facuta...chestia aia cu map??
        // https://www.baeldung.com/mockito-behavior
        //https://www.youtube.com/watch?v=d2KwvXQgQx4

        Mockito.when(keyValues.get("setId")).thenReturn("10");
        Mockito.when(converter.convert("10", Long.class)).thenReturn(10L);

        Mockito.when(keyValues.get("setName")).thenReturn("someName");
        Mockito.when(converter.convert("someName", String.class)).thenReturn("someName");

        Map<String, String> map = new HashMap<>();
        map.put("id","10");
        map.put("name","someName");

        Mockito.when(extractor.extractKeyValues("someInput")).thenReturn(map);

        TwoPropertyClass instance = deserializer.deserialize("someInput", TwoPropertyClass.class);

        assertEquals(new Long(10), instance.getId());
        assertEquals("someName", instance.getName());
    }

    static class OnePropertyClass {
        Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    static class TwoPropertyClass {
        Long id;
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}