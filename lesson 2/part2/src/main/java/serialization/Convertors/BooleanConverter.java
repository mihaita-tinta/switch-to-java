package serialization.Convertors;

public class BooleanConverter implements IGenericConverter {
    private static final Boolean DEFAULT_BOOLEAN = false;

    @Override
    public Boolean GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return DEFAULT_BOOLEAN;
        else
            return new Boolean(valueToConvert);
    }
}
