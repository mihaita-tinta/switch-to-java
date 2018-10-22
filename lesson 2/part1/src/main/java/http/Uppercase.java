package http;

public class Uppercase implements Interpreter{

    private String content;

    public Uppercase(String content) {
        this.content = content;
    }

    @Override
    public String interpret(Context c) {
        return c.lowercase(this.content);
    }
}
