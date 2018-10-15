package serialization.Convertors;

import java.time.LocalDate;

public class LocalDateConverter  implements IGenericConverter{

    @Override
    public LocalDate GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return  LocalDate.now();
        else
            return LocalDate.parse(valueToConvert);
    }
}
