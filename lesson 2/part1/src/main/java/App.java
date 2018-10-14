import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String... args)  {
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
        //D:\input          C:\Test\input
        //D:\output         C:\Test\output
        String input = "D:\\input";//TODO 0  first argument is input directory
        String output = "D:\\output";// TODO 0 second argument is the output directory

        File inputFile = new File(input);
        File outputFile = new File(output);

        try{
            verifyDirectories(inputFile,outputFile);
        }catch (InvalidDirectoryException e){
            e.printStackTrace();
            return;
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {List<File> lista = createList(inputFile);
                               lista.stream()
                                    .forEach(e -> creeazaSiModifica(e,inputFile,outputFile));

            };
        executor.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS);

    }

    public static List<File> createList(File inputFile){
        File[] files = inputFile.listFiles();
        List<File> lista = new ArrayList<File>(Arrays.asList(files)).stream()
                .filter(i -> !i.isDirectory())
                .collect(Collectors.toList());

        if(lista.size()== 0){
            System.out.println("Nu sunt fisiere in directorul sursa");
        }
        return lista;
    }

    public static void creeazaSiModifica(File file, File inDirectory, File outDirectory){
        PrintWriter out = null;
        try {
            String text  = Files.lines(file.toPath())
                    .map(e ->e.toUpperCase())
                    .collect(Collectors.joining("\n"));

            String newName = file.getName().replaceFirst("\\..+","")+".transformed";
            File fileTransformed = new File(inDirectory+ "\\"+newName);
            out = new PrintWriter(fileTransformed);
            out.print(text);

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
        }finally {
            out.close();
        }
    }

    public static void verifyDirectories(File inputFile, File outputFile) throws InvalidDirectoryException {
        if(!inputFile.isDirectory() | !outputFile.isDirectory()){
            throw new InvalidDirectoryException();
        }
    }
}

