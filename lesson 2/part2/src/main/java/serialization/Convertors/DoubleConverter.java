package serialization.Convertors;

public class DoubleConverter implements IGenericConverter {
    private static final Double DEFAULT_DOUBLE = 0.0;

    @Override
    public Double GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return DEFAULT_DOUBLE;
        else
            return new Double(valueToConvert);
    }
}
