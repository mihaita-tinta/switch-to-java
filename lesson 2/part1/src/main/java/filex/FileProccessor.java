package filex;

import java.io.*;

public class FileProccessor {

    private File file;

    public FileProccessor(File file) {
        this.file = file;
    }

    public void transform(String output) {
        try {

            FileInputStream fis1     = new FileInputStream(file);
            FileOutputStream fos1    = new FileOutputStream(new File(output + "//" + file.getName()));

            int c;
            while ((c = fis1.read()) != -1) {
                c = Character.toUpperCase(c);
                fos1.write(c);
            }
            fos1.close();
            fis1.close();
            String newFileName = file.getName().substring(file.getName().lastIndexOf(".") + 1) + ".transformed";

            file.renameTo(new File(output + "//" + newFileName));

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
