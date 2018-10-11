package server.tcpserver;

import server.tcpserver.codec.Codec;
import server.tcpserver.codec.CodecPipeline;

import java.nio.ByteBuffer;
import java.util.*;

public class Connection {
    private TcpServer server;
    private CodecPipeline codecPipeline;
    private MessageHandler handler;
    private UUID id = UUID.randomUUID();

    private ByteBuffer buffer = ByteBuffer.allocate(0);

    public Connection(TcpServer server, CodecPipeline codecPipeline, MessageHandler handler) {
        this.server = server;
        this.codecPipeline = codecPipeline;
        this.handler = handler;
    }

    public void send(Object data) {
        Iterator<Codec> pipeline = codecPipeline.getWriteOrder();
        while (pipeline.hasNext()) {
            Codec codec = pipeline.next();
            try {
                data = codec.encode(data);
            } catch (ClassCastException e) {
                // maybe we need to skip some codec
            }
        }
        server.send(this, (ByteBuffer) data);
    }

    public void received(ByteBuffer data) {
        Iterator<Codec> pipeline = codecPipeline.getReadOrder();

        if (buffer.remaining() > 0) {
            ByteBuffer newBuffer = ByteBuffer.allocate(data.remaining() + buffer.remaining());
            newBuffer.put(buffer);
            newBuffer.put(data);
            newBuffer.flip();
            buffer = newBuffer;
        } else {
            buffer = data;
        }

        List<Object> previousStage = Collections.singletonList(buffer);

        boolean hasFinalObjects = true;

        while (pipeline.hasNext()) {
            if (previousStage.isEmpty()) {
                hasFinalObjects = false;
                break;
            }

            Codec codec = pipeline.next();
            List<Object> currentStage = new LinkedList<>();

            for (Object obj : previousStage) {
                try {
                    currentStage.addAll(codec.decode(obj));
                } catch (ClassCastException e) { // if we need to skip something
                    currentStage.add(obj);
                }
            }

            previousStage = currentStage;
        }

        if (hasFinalObjects) {
            List<Object> responses = new LinkedList<>();
            for (Object obj : previousStage) {
                responses.addAll(handler.handle(obj));
            }

            for (Object response : responses) {
                send(response);
            }
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
