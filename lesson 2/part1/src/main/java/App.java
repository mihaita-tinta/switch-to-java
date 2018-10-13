import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

        String input = "D:\\java\\projects\\switch-to-java\\switch-to-java\\_input";//TODO 0  first argument is input directory
        String output = "D:\\java\\projects\\switch-to-java\\switch-to-java\\_output";// TODO 0 second argument is the output directory

        File fi = new File(input);
        File fo = new File(output);

        FileOutputStream out = null;

        if (fi.isDirectory() && fo.isDirectory()) {
            List<File> fls = Arrays.asList(fi.listFiles());

            Iterator<File> it = fls.iterator();
            while (it.hasNext()) {
                try {
                    File in = it.next();
                    FileInputStream in1 = new FileInputStream(in);
                    out = new FileOutputStream(new File(output + "//" + in.getName()));

                    int c;
                    while ((c = in1.read()) != -1) {
                        c=Character.toUpperCase(c);
                        out.write(c);
                    }
                    out.close();
                    in1.close();
                    String newFileName = in.getName().substring(in.getName().lastIndexOf(".") + 1) + ".csv";
                    System.out.println(newFileName);

                    in.renameTo(new File(output + "//" + newFileName));

                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {

                } finally {

                }

            }

        }

    }
}
