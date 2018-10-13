package http;

import com.sun.tools.javac.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InterpreterContext {

    public String lowercase(String content) {
        return StringUtils.toLowerCase(content);
    }

    public String uppercase(String content) {
        return StringUtils.toUpperCase(content);
    }

    public String now(String zone) {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        if (zones.contains(zone)) {
            return LocalDateTime.now(ZoneId.of(zone)).toString();
        } else {
            return LocalDateTime.now().toString();
        }
    }

    public String path(String path) {
        File file = new File(path);
        List<String> result = new ArrayList<>();
        if (!file.exists()) {
            System.out.println("File not exists");
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                result = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
            }
        } else {
            try {
                result = Files.readAllLines(Paths.get(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        return result.toString();
    }
}
