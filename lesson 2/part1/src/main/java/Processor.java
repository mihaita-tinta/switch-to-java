import java.io.File;

public interface Processor<S, T> {

    public T process(S content);
}
