package server.tcpserver;

import server.tcpserver.codec.CodecPipelineFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class TcpServer {
    protected CodecPipelineFactory codecPipelineFactory;
    protected MessageHandler messageHandler;
    protected int port;
    protected boolean running;

    public TcpServer(int port, CodecPipelineFactory codecPipelineFactory, MessageHandler messageHandler) {
        this.codecPipelineFactory = codecPipelineFactory;
        this.messageHandler = messageHandler;
        this.port = port;
    }

    public abstract void start() throws IOException;
    public abstract void stop();
    public abstract void send(Connection connection, ByteBuffer data);
    public abstract void closeConnection(Connection connection);


    protected Connection startNewConnection() {
        return new Connection(this, codecPipelineFactory.newCodecPipeline(), messageHandler);
    }

    public boolean isRunning() {
        return running;
    }

    protected void setRunning(boolean running) {
        this.running = running;
    }
    protected int getPort() {
        return port;
    }
}
