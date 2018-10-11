package server.tcpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.tcpserver.codec.CodecPipelineFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class NioServer extends TcpServer {
    private static final Logger LOG = LoggerFactory.getLogger(NioServer.class);

    private static final int BUFFER_SIZE = 1024;

    private int port;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Map<Connection, ConnectionData> connections = new HashMap<>();
    private Map<SocketChannel, Connection> socketToConnection = new HashMap<>();
    private Thread serverThread;

    public NioServer(int port, CodecPipelineFactory codecPipelineFactory, MessageHandler messageHandler) {
        super(port, codecPipelineFactory, messageHandler);
    }

    @Override
    public void start() throws IOException {
        if (this.isRunning()) {
            this.stop();
        }

        this.setRunning(true);

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket().bind(new InetSocketAddress(getPort()));
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverLoop();
                } catch (IOException e) {
                    LOG.error("Got server exception", e);
                    stop();
                }
            }
        });
        serverThread.start();
    }

    private void serverLoop() throws IOException {
        while (this.isRunning()) {
            selector.select();
            Iterator keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                // this is necessary to prevent the same key from coming up
                // again the next time around.
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    accept(key);
                }
                else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel;
        try {
            channel = serverChannel.accept();
            channel.configureBlocking(false);
        } catch (IOException e) {
            LOG.error("Got error in accept()", e);
            return;
        }

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        LOG.info("Connected to: " + remoteAddr);

        Connection connection = startNewConnection();

        ConnectionData connData = new ConnectionData(channel, ByteBuffer.allocate(BUFFER_SIZE));

        connections.put(connection, connData);
        socketToConnection.put(channel, connection);

        try {
            SelectionKey readKey = channel.register(this.selector, SelectionKey.OP_READ);
            connData.setSelectionKey(readKey);
        } catch (ClosedChannelException e) {
            LOG.error("Got exception in accept()", e);
            closeConnection(connection);
        }
    }

    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();

        Connection connection = socketToConnection.get(channel);
        ConnectionData connData = connections.get(connection);

        ByteBuffer buffer = connData.getBuffer();
        int numRead;

        try {
            numRead = channel.read(buffer);
        } catch (IOException e) {
            LOG.error("Got exception during read, closing connection", e);
            closeConnection(connection);
            return;
        }

        if (numRead == -1) {
            closeConnection(connection);
            return;
        }

        buffer.flip();
        connection.received(buffer);
        buffer.clear();
    }

    @Override
    public void stop() {
        this.setRunning(false);
        List<Connection> connectionList = new LinkedList<>(connections.keySet());

        for (Connection connection : connectionList) {
            closeConnection(connection);
        }

        try {
            this.selector.close();
            this.serverSocketChannel.close();
        } catch (IOException e) {
            LOG.error("Exception in stop", e);
        }

        try {
            serverThread.join(5000);
        } catch (InterruptedException e) {
            LOG.error("Exception in stop", e);
            serverThread.stop();
        }
    }

    @Override
    public void send(Connection connection, ByteBuffer data) {
        ConnectionData connData = connections.get(connection);
        try {
            while (data.hasRemaining()) {
                connData.getChannel().write(data);
            }
        } catch (IOException e) {
            LOG.error("Got exception in write", e);
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        ConnectionData connectionData = connections.get(connection);

        connections.remove(connectionData);
        socketToConnection.remove(connectionData.getChannel());


        Socket socket = connectionData.getChannel().socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        LOG.info("Connection closed: " + remoteAddr);
        connectionData.getSelectionKey().cancel();
        try {
            connectionData.getChannel().close();
        } catch (IOException e) {
            LOG.error("Got exception in channel close", e);
        }
    }

    private static class ConnectionData {
        private SocketChannel channel;
        private ByteBuffer buffer;
        private SelectionKey selectionKey;

        public ConnectionData(SocketChannel channel, ByteBuffer buffer) {
            this.channel = channel;
            this.buffer = buffer;
        }

        public SocketChannel getChannel() {
            return channel;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }

        public SelectionKey getSelectionKey() {
            return selectionKey;
        }

        public void setSelectionKey(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }
    }
}
