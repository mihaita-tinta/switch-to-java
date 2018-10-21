import filex.DirectoryScanner;
import filex.FileHandler;
import filex.FileProcessor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        String input                = "D:\\java\\projects\\switch-to-java\\switch-to-java\\_input";//TODO 0  first argument is input directory
        String output               = "D:\\java\\projects\\switch-to-java\\switch-to-java\\_output";// TODO 0 second argument is the output directory

        DirectoryScanner scanner    = new DirectoryScanner(input);
        FileProcessor processor     = new FileProcessor(output);

        FileHandler handler         = FileHandler.getInstance(scanner, processor);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        Runnable task = () -> {
            System.out.println("Process Files - thread: " + Thread.currentThread().getName());
            handler.scan().process();
        };
        executorService.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }
}
