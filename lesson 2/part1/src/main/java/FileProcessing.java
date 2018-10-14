import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class FileProcessing implements Observer {
    String input;
    String output;

    FileProcessing(String input, String output)
    {
        this.input = input;
        this.output = output;
    }

    public void StartFileProcessing() throws Exception {
        FileDetection fileDetection = new FileDetection();
        fileDetection.addObserver(this);
        fileDetection.DetectCurrentFiles(input);

        FileContentConversion fileConverter = new FileContentConversion(fileDetection.GetFiles());
        fileConverter.ConvertFiles();

        FileSaver fileSaver = new FileSaver(fileConverter.GetConvertedDataMap());
        fileSaver.Save(output);

        fileDetection.CheckForNewFile(input, 10);

    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("received new file " + ((File)arg).getName());
        try {
            ProcessNewFile((File)arg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ProcessNewFile(File newFile) throws Exception {
        File[] fileWrapper = new File[1];
        fileWrapper[0] = newFile;

        FileContentConversion fileConverter = new FileContentConversion(fileWrapper);
        fileConverter.ConvertFiles();

        FileSaver fileSaver = new FileSaver(fileConverter.GetConvertedDataMap());
        fileSaver.Save(output);

    }
}
