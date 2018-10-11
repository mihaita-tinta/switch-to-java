import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

        String input = "C:\\Test\\input";//TODO 0  first argument is input directory
        String output = "C:\\Test\\output";// TODO 0 second argument is the output directory

        File inputFile = new File(input);
        File outputFile = new File(output);

        if(!inputFile.isDirectory() | !outputFile.isDirectory()){
            System.out.println("Fisierele nu sunt directoare");
        }

        File[] files = inputFile.listFiles();

        if(files.length <= 0){
            System.out.println("Nu avem fisiere in directorul input");
        }

        System.out.println("Creez o lista de fisiere - scot directoarele din lista initiala");
        List<File> lista = new ArrayList<File>(Arrays.asList(files)).stream()
                .filter(i -> !i.isDirectory())
                .collect(Collectors.toList());

        System.out.println("### AICI INCEP");
        long in = System.currentTimeMillis();
        lista.parallelStream()
                .forEach(e -> creeazaSiModifica(e,inputFile,outputFile));
        long out = System.currentTimeMillis();
        System.out.println(out-in);






//        System.out.println("### Cazul for");
//        long in = System.currentTimeMillis();
//        for(File e:lista){
//            creeazaSiModifica(e,inputFile,outputFile);
//        }
//        long out = System.currentTimeMillis();
//        System.out.println(out-in);
    }

    public static void creeazaSiModifica(File file, File inDirectory, File outDirectory){
//        if(file.isDirectory()){ return;}
        try {
            String text  = Files.lines(file.toPath())
                    .map(e ->e.toUpperCase())
                    .collect(Collectors.joining("\n"));

            String newName = file.getName().replaceFirst("\\..+","")+".transformed";
            File fileTransformed = new File(inDirectory+ "\\"+newName);
            PrintWriter out = new PrintWriter(fileTransformed);
            out.print(text);
            out.close();

            if(file.renameTo(new File(outDirectory+"\\"+file.getName()))){
                System.out.println("File "+file.getName() +" was moved succesfully");
            }
            else{
                System.out.println("File "+file.getName() + " : Problem!!" );
            }

            if(fileTransformed.renameTo(new File(outDirectory+"\\"+fileTransformed.getName()))){
                System.out.println("File "+fileTransformed.getName()+" was moved succesfully");
            }
            else{
                System.out.println("File "+fileTransformed.getName() + " : Problem!!" );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

