package server.tcpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.tcpserver.codec.CodecPipelineFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ThreadedServer extends TcpServer {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServer.class);

    private static final int BUFFER_SIZE = 1024;
    private int port;
    private ServerSocket serverSocket;
    private Thread serverThread;

    // A map containing useful information for each connection (e.g. the socket)
    private Map<Connection, ConnectionData> connections = new HashMap<>();

    public ThreadedServer(int port, CodecPipelineFactory codecPipelineFactory, MessageHandler messageHandler) {
        super(port, codecPipelineFactory, messageHandler);
    }

    @Override
    public void start() throws IOException {
        if (this.isRunning()) {
            this.stop();
        }

        /*
         * You should start the server socket and create the server thread, starting it with the listenLoop()
         */
    }

    private void listenLoop() {
        while (isRunning()) {
            Socket client;

            // accept new connections


            // after the connection was accepted, initialize a Connection object
            Connection connection = startNewConnection();


            Thread clientThread;

            /*
             * start a new thread which will handle this new connection (use the clientLoop() method)
             * create the ConnectionData associated with this connection and store it in the connections map
             */
        }
    }

    private void clientLoop(Connection connection) {
        // get the socket associated with this client. use the map.

        // create a buffer and read from the socket as long as it is open

        // remember to handle closed sockets

        closeConnection(connection);
    }

    @Override
    public void stop() {
        this.setRunning(false);
        // close all the connection sockets

        // close the server socket

        // try to cleanly wait for serverThread to finish (use the join() method)

        // try to avoid calling serverThread.kill() or only call it as a last resort.
    }

    @Override
    public void send(Connection connection, ByteBuffer data) {
        // retrieve the socket associated with the connectiond and perform a write
    }

    @Override
    public void closeConnection(Connection connection) {
        // close the socket of the connection and cleanly close the client thread

    }

    private static class ConnectionData {
        private Socket socket;
        private Thread thread;

        public ConnectionData(Socket socket, Thread thread) {
            this.socket = socket;
            this.thread = thread;
        }

        public Socket getSocket() {
            return socket;
        }

        public Thread getThread() {
            return thread;
        }
    }
}
