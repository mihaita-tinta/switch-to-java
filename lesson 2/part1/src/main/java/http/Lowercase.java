package http;

public class Lowercase implements Interpreter{

    private String content;

    public Lowercase(String content) {
        this.content = content;
    }

    @Override
    public String interpret(Context c) {
        return c.lowercase(this.content);
    }
}
