import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessFiles {

    public static File transformFile(File file) throws IOException
    {
        System.out.println("Transform File method called for " + file.getName());
        Stream<String> stream = Files.lines(file.toPath());
        List<String> list = stream.map(String::toUpperCase).collect(Collectors.toList());
        String fileTransformed = file.getName().substring(0, file.getName().indexOf('.')) + ".transformed";
        File transformedFile = new File(file.getParentFile().getAbsolutePath().concat("/" + fileTransformed));
        Files.write(transformedFile.toPath(), list);

        return transformedFile;
    }

    public static void execute(String input, String output) {
        System.out.println("Execute method called");
        File inputDirectory = new File(input);
        File outputDirectory = new File(output);
        if (!validDirectories(inputDirectory, outputDirectory)) {
            File[] files = inputDirectory.listFiles();
            if (files != null) {
                System.out.println(files.length + " files found");
                Arrays.stream(files).parallel().forEach(file -> {
                    try {
                        System.out.println("Thread " + Thread.currentThread().getName());
                        System.out.println("File processing " + file.getName());
                        File transformedFile = ProcessFiles.transformFile(file);
                        Files.move(file.toPath(), Paths.get(outputDirectory.toPath() + "/" + file.getName()));
                        Files.move(transformedFile.toPath(), Paths.get(outputDirectory.toPath() + "/" + transformedFile.getName()));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        } else {
            System.out.println("Please provide valid input/output folders");
            System.exit(0);
        }
    }

    private static boolean validDirectories(File input, File output)
    {
        return input.exists()
                && input.isDirectory()
                && output.exists()
                && output.isDirectory();
    }
}
