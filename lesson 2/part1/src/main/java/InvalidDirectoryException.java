public class InvalidDirectoryException extends Exception{

    public InvalidDirectoryException(){
        super("Path name is not a valid directory");
    }
}
