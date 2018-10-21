package filex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

public class FileHandler {

    private DirectoryScanner scanner;
    private FileProcessor processor;
    private List<File> files;

    private static FileHandler instance;
    private static final Logger log         = LoggerFactory.getLogger(FileHandler.class);

    /**
     * @param scanner
     * @param processor
     */
    private FileHandler(DirectoryScanner scanner, FileProcessor processor) {
        this.scanner    = scanner;
        this.processor = processor;
    }

    /**
     * @param scanner
     * @param processor
     * @return FileHandler
     */
    public static FileHandler getInstance(DirectoryScanner scanner, FileProcessor processor) {
        if (null==instance) {
            instance = new FileHandler(scanner, processor);
        }
        return instance;
    }

    public FileHandler scan() {
        files = scanner.asList();
        return this;
    }

    public void process() {
        if (files.size() > 0) {
            for (File f : files) {
                try {
                    processor.transform(f);
                } catch (IllegalArgumentException e) {
                    log.info("Exception: " + e.getMessage());
                }
            }
        }
    }
}
