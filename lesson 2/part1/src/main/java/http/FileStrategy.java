package http;

public class FileStrategy implements IStrategy {
    @Override
    public String getText(String string) {
        return string;
    }
}
