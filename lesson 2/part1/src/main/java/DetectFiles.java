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
    private String inputDirectory;

    private String outputDirectory;

    public DetectFiles(String input, String output)
    {
        inputDirectory = input;
        outputDirectory = output;
    }

    void scanFolder()
    {
        System.out.println("Scan folder method called");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> transformAndMoveFiles(inputDirectory, outputDirectory);
        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }

    private void transformAndMoveFiles(String input, String output)
    {
        System.out.println("Transform and move method called");
        File inputDirectory = new File(input);
        File outputDirectory = new File(output);
        if (inputDirectory.isDirectory() && outputDirectory.exists() && outputDirectory.isDirectory()) {
            File[] files = inputDirectory.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(file -> {
                    try {
                        Stream<String> stream = Files.lines(file.toPath());
                        List<String> list = stream.map(String::toUpperCase).collect(Collectors.toList());
                        String newExtension = file.getName().substring(0, file.getName().indexOf('.')) + ".transformed";
                        File transformedFile = new File(inputDirectory.getPath().concat("/" + newExtension));
                        Files.write(transformedFile.toPath(), list);
                        Files.move(file.toPath(), Paths.get(outputDirectory.toPath() + "/" + file.getName()));
                        Files.move(transformedFile.toPath(), Paths.get(outputDirectory.toPath() + "/" + transformedFile.getName()));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        }
    }
}
