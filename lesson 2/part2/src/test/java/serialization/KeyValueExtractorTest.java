package serialization;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class KeyValueExtractorTest {

    // TODO 2 - fix failing tests
    String carJson = "{" +
                        "\"id\":\"2\"," +
                        "\"number\":\"IL12ABC\"" +
                    "}";
    KeyValueExtractor keyValueExtractor = new KeyValueExtractor();

    @Test
    public void when_keyIsInJson_expect_correctValueIsReturned() {
        Map<String, String> keyValues = keyValueExtractor.extractKeyValues(carJson);

        assertEquals(2, keyValues.size());
        assertEquals("IL12ABC", keyValues.get("number"));
        assertEquals("2", keyValues.get("id"));
    }

    @Test
    public void when_extractKeyValues_expectAllKeysAreProvided() {
        List<String> keys = keyValueExtractor.getKeys(carJson);
        assertEquals(2, keys.size());
    }

    @Test
    public void when_keyIsInNotJson_expect_nullIsReturned() {
        Map<String, String> keyValues = keyValueExtractor.extractKeyValues(carJson);

        assertNull(keyValues.get("thisKeyIsNotInJson"));
    }

}
