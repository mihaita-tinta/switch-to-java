import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DetectFiles
{
    private String input;

    private String output;

    DetectFiles(String input, String output)
    {
        this.input = input;
        this.output = output;
    }

    void scanFolder()
    {
        System.out.println("Scan folder method called");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> ProcessFiles.execute(this.input, this.output), 0, 10, TimeUnit.SECONDS);
    }
}
