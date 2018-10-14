import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class
FileSaver {
    private static final String newExtensionName = "transformed";
    private Map<File,List<String>> convertedContentMap;

    public FileSaver(Map<File,List<String>> convertedContentMap)
    {
        this.convertedContentMap = convertedContentMap;
    }

    public void Save(String pathToMove) throws IOException {
        for(Map.Entry<File,List<String>> entry : convertedContentMap.entrySet())
        {
            //move original file
            MoveFile(entry.getKey(),pathToMove);

           File finalResult = CompletableFuture.supplyAsync(() -> {
                File file = entry.getKey();
                List<String> content  = entry.getValue();
                String fileNameWithNewExt = ChangeFileExtension(file,newExtensionName);
                File newFile = CreateNewFile(fileNameWithNewExt);
                WriteToNewFile(content, fileNameWithNewExt);

                return newFile;
            }).thenApply((res) -> {
               //move transformed file
               MoveFile(res,pathToMove);

               return res;
            }).join();
        }
    }

    private void WriteToNewFile(List<String> content, String fileNameWithnewExt) {
        try {
            Files.write(Paths.get(fileNameWithnewExt), content, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File CreateNewFile(String fileNameWithnewExt) {
        File newFile = new File(fileNameWithnewExt);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    private String ChangeFileExtension(File file, String newExtension)
    {
        int index = file.getName().lastIndexOf('.');
        String name = file.getName().substring(0,index+1);

        return new String(file.getParent() + "\\" + name + newExtension);
    }

    private void MoveFile(File file, String pathToMove)
    {
        try {
            Path result = Files.move(Paths.get(file.getAbsolutePath()),Paths.get(pathToMove
                    +"\\" + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
