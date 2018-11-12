package http;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Now implements Interpreter{

    private String content;

    public Now(String content) {
        this.content = content;
    }

    @Override
    public String interpret(Context c) {
        return c.now(this.content);
    }
}
