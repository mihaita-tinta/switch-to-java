package server.tcpserver;

import java.util.List;

public interface MessageHandler<R, S> {

    public List<S> handle(R message);
}
