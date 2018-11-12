package http;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.File;

public class Context {

    public String lowercase(String content) {
        return content.toLowerCase();
    }

    public String uppercase(String content) {
        return content.toUpperCase();
    }

    public String now(String content) {
        LocalDateTime dt = LocalDateTime.now();
        ZoneId zone;
        if (ZoneId.getAvailableZoneIds().contains(content))
            zone = ZoneId.of(content);
        else zone = ZoneId.of("UTC");

        return dt.atZone(zone).toString();
    }

    public String path(String content) {
        File f = new File(content);
        if(f.exists() && !f.isDirectory()) {
            // do something
        }

        return "";
    }
}
