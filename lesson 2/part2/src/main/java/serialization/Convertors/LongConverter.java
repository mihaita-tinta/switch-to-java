package serialization.Convertors;

public class LongConverter implements IGenericConverter {
    private static final Long DEFAULT_LONG = 0L;

    @Override
    public Long GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
             return DEFAULT_LONG;
        else
            return new Long(valueToConvert);
    }
}
