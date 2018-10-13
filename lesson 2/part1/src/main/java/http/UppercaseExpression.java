package http;

public class UppercaseExpression implements Expression {

    private String content;

    public UppercaseExpression(String content) {
        this.content = content;
    }

    @Override
    public String interpret(InterpreterContext ic) {
        return ic.uppercase(content);
    }
}
