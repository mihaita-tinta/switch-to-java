import org.junit.Test;

import java.io.*;

import static org.junit.Assert.fail;

public class Lesson2_2_StreamTest {


    @Test
    public void test_readWriteFile() throws IOException {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            in = Lesson2_2_StreamTest.class.getClassLoader()
                    .getResourceAsStream("src/test/resources/a.txt");
            out = new FileOutputStream("b.txt");

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    @Test
    public void test_tryWithResources() throws IOException {
        try (InputStream in = Lesson2_2_StreamTest.class.getClassLoader()
                    .getResourceAsStream("src/test/resources/a.txt");
             FileOutputStream out = new FileOutputStream("b.txt")) {

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }
    }

    @Test( expected = FileNotFoundException.class)
    public void test_fileNotFound() throws IOException {
        File f = new File("C:/java/hello");
        OutputStream os = new FileOutputStream(f);
        fail();
    }
}
