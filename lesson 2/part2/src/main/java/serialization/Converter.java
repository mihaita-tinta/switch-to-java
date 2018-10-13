package serialization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Converter {

    public Object convert(String from, Class<?> target) {
        // TODO 1 based on the target class you need to convert it to desired type

        if (target.getName().equals(byte.class.getName())) {
            return Byte.parseByte(from != null ? from : "0");
        }

        if (target.getName().equals(short.class.getName())) {
            return Short.parseShort(from != null ? from : "0");
        }

        if (target.getName().equals(int.class.getName())) {
            return Integer.parseInt(from != null ? from : "0");
        }

        if (target.getName().equals(long.class.getName())) {
            return Long.parseLong(from != null ? from : "0");
        }

        if (target.getName().equals(float.class.getName())) {
            return Float.parseFloat(from != null ? from : "0");
        }

        if (target.getName().equals(double.class.getName())) {
            return Double.parseDouble(from != null ? from : "0");
        }

        if (target.getName().equals(boolean.class.getName())) {
            return Boolean.parseBoolean(from != null ? from : "false");
        }

        if (target.getName().equals(Byte.class.getName())) {
            return Byte.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Short.class.getName())) {
            return Short.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Integer.class.getName())) {
            return Integer.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Long.class.getName())) {
            return Long.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Float.class.getName())) {
            return Float.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Double.class.getName())) {
            return Double.valueOf(from != null ? from : "0");
        }

        if (target.getName().equals(Boolean.class.getName())) {
            return Boolean.valueOf(from != null ? from : "false");
        }

        if (target.getName().equals(LocalDate.class.getName())) {
            return LocalDate.parse(from);
        }

        if (target.getName().equals(LocalDateTime.class.getName())) {
            return LocalDateTime.parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        if (target.getName().equals(String.class.getName())) {
            return String.valueOf(from);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
