import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Lesson2_2_FileTest {


    @Test
    public void test_readWriteFile() throws IOException {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            in = Lesson2_2_FileTest.class.getClassLoader()
                    .getResourceAsStream("a.txt");
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
        try (InputStream in = Lesson2_2_FileTest.class.getClassLoader()
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

    @Test
    public void testReadFile() throws IOException {
        File file = new File("C:\\workplace\\github\\switch-to-java\\lesson 2\\src\\test\\resources\\a.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            System.out.println(st);
    }

    @Test
    public void testReadFile_nio_line_by_line() throws IOException {
        String path = "C:\\workplace\\github\\switch-to-java\\lesson 2\\src\\test\\resources\\a.txt";
        Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)
            .stream()
            .forEach(System.out :: println);
    }

    @Test
    public void testReadFile_nio_bytes() throws IOException {
        String path = "C:\\workplace\\github\\switch-to-java\\lesson 2\\src\\test\\resources\\a.txt";
        System.out.println(new String(Files.readAllBytes(Paths.get(path))));
    }

    @Test
    public void testMoveFile() throws IOException {
        Path from = Paths.get("C:\\workplace\\github\\switch-to-java\\lesson 2\\src\\test\\resources\\a.txt");
        Path to = Paths.get("C:\\workplace\\github\\switch-to-java\\lesson 2\\src\\test\\resources\\aa.txt");
        Path result = Files.move(from, to);
        if (result == null) {
            fail("fail to move file");
        } else {
            assertFalse(from.toFile().exists());
            assertTrue(to.toFile().exists());
            // revert changes
            Files.move(to, from);
        }

    }
}
