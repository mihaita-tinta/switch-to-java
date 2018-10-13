package http;

public class LowercaseExpression implements Expression {

    private String content;

    public LowercaseExpression(String content) {
        this.content = content;
    }

    @Override
    public String interpret(InterpreterContext ic) {
        return ic.lowercase(content);
    }
}
