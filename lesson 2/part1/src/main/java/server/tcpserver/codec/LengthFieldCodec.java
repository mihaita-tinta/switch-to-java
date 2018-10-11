package server.tcpserver.codec;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class LengthFieldCodec implements Codec<ByteBuffer, ByteBuffer> {

    @Override
    public List<ByteBuffer> decode(ByteBuffer data) {
        List<ByteBuffer> result = new LinkedList<>();

        while (true) {
            if (data.remaining() < 4) {
                break;
            }

            int size = data.getInt(data.position());
            if (data.remaining() - 4 < size) {
                break;
            }

            // actually consume the 4 bytes now
            data.position(data.position() + 4);
            ByteBuffer frame = ByteBuffer.allocate(size);
            data.get(frame.array());
            result.add(frame);
        }

        return result;
    }

    @Override
    public ByteBuffer encode(ByteBuffer object) {
        ByteBuffer result = ByteBuffer.allocate(4 + object.remaining());
        result.putInt(object.remaining());
        result.put(object);
        return result;
    }
}
