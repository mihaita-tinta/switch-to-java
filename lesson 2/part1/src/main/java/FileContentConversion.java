import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileContentConversion
    {
        private File[] listOfFiles;
        private ConcurrentHashMap<File,List<String>> convertedContentMap;

        FileContentConversion(File[] listOfFiles) throws Exception {
            if(listOfFiles == null)
                throw  new Exception("list of files is null");
            int numOfFile = listOfFiles.length;
            this.listOfFiles = new File[numOfFile];
            this.listOfFiles = Arrays.copyOf(listOfFiles,numOfFile);
        }

        public void ConvertFiles()
        {   convertedContentMap = new ConcurrentHashMap<File, List<String>>();
            for(File file: listOfFiles)
            {
                Runnable convert = (()->{
                    try {
                       List<String> convertedContent =  this.ConvertFileContentToUpperCase(file);
                       convertedContentMap.put(file,convertedContent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                convert.run();
            }
        }

        private List<String> ConvertFileContentToUpperCase(File file) throws IOException {
           List<String> content = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
           content.replaceAll(String::toUpperCase);

           return content;
        }

        public Map<File,List<String>> GetConvertedDataMap()
        {
            return convertedContentMap;
        }
    }
