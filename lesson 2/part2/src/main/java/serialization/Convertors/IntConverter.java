package serialization.Convertors;

public class IntConverter implements IGenericConverter {
    private static final Integer DEFAULT_INT = 0;

    @Override
    public Integer GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return DEFAULT_INT;
        else
            return new Integer(valueToConvert);
    }
}
