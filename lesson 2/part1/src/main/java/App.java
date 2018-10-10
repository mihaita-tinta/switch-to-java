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


        String input = "D:\\input";//TODO 0  first argument is input directory
        String output = "D:\\output";// TODO 0 second argument is the output directory

        CopiazaFisiere c = new CopiazaFisiere(input, output);
        c.procesare();

//        File inputFile = new File(input);
//            Arrays.asList(inputFile.listFiles())
//                    .stream()
//                    .forEach(System.out::println);
//
//            List<File> ls = Arrays.asList(inputFile.listFiles());
//            for(int i=0; i<ls.size();i++){
//                System.out.println(ls.get(i));
//                Path from = Paths.get(String.valueOf(ls.get(i)));
//                Path to = Paths.get(String.valueOf(ls.get(i)));
//                Path result = Files.move(from, to);
//                if (result == null) {
//                    System.out.println("fail to move file");

                }
}
