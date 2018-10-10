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

        if (args.length > 1) {
            String input = args[0]; //"/Users/sandu.velea/personal/java/fisiere/1";//TODO 0  first argument is input directory
            String output = args[1]; //"/Users/sandu.velea/personal/java/fisiere/2";// TODO 0 second argument is the output directory
            DetectFiles.transformAndMoveFiles(input, output);
        }
    }
}
