import java.io.File;

public class App {

    /**
     * This application
     * @param args
     */
    public static void main(String... args) throws Exception {
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

        String input = "C:\\Users\\Thinkpad\\Desktop\\Java Test";
        String output = "C:\\Users\\Thinkpad\\Desktop\\Java Test Output";
        /*FileDetection fileDetection = new FileDetection();
        fileDetection.DetectCurrentFiles(input);

        FileContentConversion fileConverter = new FileContentConversion(fileDetection.GetFiles());
        fileConverter.ConvertFiles();
        FileSaver fileSaver = new FileSaver(fileConverter.GetConvertedDataMap());
        fileSaver.Save(output);*/

        FileProcessing fileProcessing = new FileProcessing(input,output);
        fileProcessing.StartFileProcessing();
    }
}
