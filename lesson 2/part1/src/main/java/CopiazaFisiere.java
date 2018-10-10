import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class CopiazaFisiere {

    public String input;
    public String output;

    CopiazaFisiere(String input, String output){
        this.input = input;
        this.output = output;

    }

    public void procesare(){
        File inputFile = new File(input);
        File outputFile = new File(output);

        if(!inputFile.isDirectory() | !outputFile.isDirectory() ){
            System.out.println("Fisierele sunt goale");
            return;
        }

        List<File> files = Arrays.asList(inputFile.listFiles());
        if (files.size() > 0){
            System.out.println("Avem fisiere");
        }
//        Files.readAllLines(e.toPath())
        files.stream()
                .filter(e-> {  });
                //.forEach(e -> System.out.println(e));


    }



}
