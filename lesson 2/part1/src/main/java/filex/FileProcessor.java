package filex;

import java.io.*;

public class FileProcessor {

    private String output;

    public FileProcessor(String output) {
        this.output = output;
    }

    public void transform(File file) throws IllegalArgumentException {

        if (file.isFile()) {
            try {

                String newFileName      = file.getName().substring(0, file.getName().lastIndexOf('.')) + ".transformed";
                FileInputStream fis1    = new FileInputStream(file);
                FileOutputStream fos1   = new FileOutputStream(new File(output + "//" + newFileName));

                int c;
                while ((c = fis1.read()) != -1) {
                    c = Character.toUpperCase(c);
                    fos1.write(c);
                }
                fos1.close();
                fis1.close();

                file.renameTo(new File(output + "//" + file.getName()));

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(file.getName() + " is not a file");
        }
    }
}
