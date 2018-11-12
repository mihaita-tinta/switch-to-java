package http;

public class Path implements Interpreter{

    private String content;

    public Path(String content) {
        this.content = content;
    }

    @Override
    public String interpret(Context c) {
        return c.path(this.content);
    }
}
