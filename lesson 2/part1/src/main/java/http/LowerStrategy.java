package http;

public class LowerStrategy implements IStrategy {
    @Override
    public String getText(String string) {
        return string.toLowerCase();
    }
}
