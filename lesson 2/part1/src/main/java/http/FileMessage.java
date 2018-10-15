package http;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileMessage extends Message {
    private String message;

    public FileMessage(String message){
        this.message=message;
    }
    @Override
    public  String convertMessage(){
        String s = this.message.substring(5);
        File inputFile = new File(s);
        if(inputFile.isDirectory()){
            File[] files = inputFile.listFiles();
            String text = Arrays.stream(files)
                    .map(e->e.getName())
                    .collect(Collectors.joining("\n"));
            return text;
        }else{
            String text  = null;
            try {
                text = Files.lines(inputFile.toPath())
                        .collect(Collectors.joining("\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
