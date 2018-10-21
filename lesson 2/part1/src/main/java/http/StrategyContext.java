package http;

public class StrategyContext {

    private IStrategy strategy;
    private String myString;

    public StrategyContext(String string) {
        String s[] = string.split(":");
        if (s.length==2) {
            switch (s[0]) {
                case "lowercase":
                    myString = s[1];
                    strategy = new LowerStrategy();
                    break;
                case "uppercase":
                    myString = s[1];
                    strategy = new UpperStrategy();
                    break;
                case "now":
                    myString = s[1];
                    strategy = new TimeStrategy();
                    break;
                case "path":
                    myString = s[1];
                    strategy = new FileStrategy();
                    break;
                default:
                     throw new IllegalArgumentException("Unknown strategy");
            }
        } else {
            throw new IllegalArgumentException("Unknown format");
        }
    }

    public String getText() {
        return strategy.getText(myString);
    }
}
