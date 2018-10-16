package serialization;

public interface ParameterizedDeserializer {

    <T> T deserialize(String string, Class<T> clasz) throws DeserializationException;
}
