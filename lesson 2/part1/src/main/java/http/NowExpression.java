package http;

public class NowExpression implements Expression {

    private String zone;

    public NowExpression(String zone) {
        this.zone = zone;
    }

    @Override
    public String interpret(InterpreterContext ic) {
        return ic.now(zone);
    }
}
