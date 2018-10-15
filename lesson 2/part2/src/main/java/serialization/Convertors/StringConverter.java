package serialization.Convertors;

public class StringConverter implements IGenericConverter {
    private static final String DEFAULT_STRING ="";

    public String GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return DEFAULT_STRING;
        else
            return new String(valueToConvert);
    }
}
