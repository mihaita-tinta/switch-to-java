package http;

public class UpperStrategy implements IStrategy {
    @Override
    public String getText(String string) {
        return string.toUpperCase();
    }
}
