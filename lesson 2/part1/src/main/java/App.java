import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    /**
     * This application
     * @param args
     */
    public static void main(String... args) {
        if (args.length != 2) {
            System.out.println("--------------------------------------------------------------\n" +
                    "--------------------------------------------------------------\n" +
                    "This program can be configured to lookup for any new files in the input folder.\n" +
                    "Once a file is detected, the program reads the content and converts it to uppercase.\n" +
                    "The result is saved into a new file with the extension .transformed.\n" +
                    "Both files are moved to the output directory\n\n" +
                            "java -cp lesson2-1.0-SNAPSHOT.jar App input/ output/ \n\n" +
                    "--------------------------------------------------------------\n" +
                    "--------------------------------------------------------------\n");
        }

        String input = "/Users/sandu.velea/personal/java/fisiere/1";//TODO 0  first argument is input directory
        String output = "/Users/sandu.velea/personal/java/fisiere/2";// TODO 0 second argument is the output directory
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
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
