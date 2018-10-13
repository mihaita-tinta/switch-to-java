package http;

public class PathExpression implements Expression {

    private String path;

    public PathExpression(String path) {
        this.path = path;
    }

    @Override
    public String interpret(InterpreterContext ic) {
        return ic.path(path);
    }
}
