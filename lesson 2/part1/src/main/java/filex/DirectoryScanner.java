package filex;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DirectoryScanner {

    private File file;
    private List<File> files;

    public DirectoryScanner(String directory) throws IllegalArgumentException {
        file = new File(directory);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Not a directory");
        }
    }

    public List<File> asList() {
        return Arrays.asList(file.listFiles());
    }

    public Iterator<File> asIterator() {
        return Arrays.asList(file.listFiles()).iterator();
    }
}
