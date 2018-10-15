package serialization.Convertors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements IGenericConverter {

    @Override
    public LocalDateTime GetConvertedValue(String valueToConvert) {
        if(valueToConvert == null)
            return LocalDateTime.now();
        else
            return LocalDateTime.parse(valueToConvert);
    }
}
