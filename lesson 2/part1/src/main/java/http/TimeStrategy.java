package http;

import java.time.LocalDate;
import java.time.ZoneId;

public class TimeStrategy implements IStrategy {
    @Override
    public String getText(String string) {
        return LocalDate.now().atStartOfDay(ZoneId.of("Europe/Bucharest")).toString();
    }
}
