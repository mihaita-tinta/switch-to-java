package server.tcpserver.codec;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringCodec implements Codec<ByteBuffer, String> {
    @Override
    public List<String> decode(ByteBuffer data) {
        byte[] chars = Arrays.copyOfRange(data.array(), data.position(), data.limit());
        data.position(data.limit());
        return Collections.singletonList(new String(chars));
    }

    @Override
    public ByteBuffer encode(String object) {
        return ByteBuffer.wrap(object.getBytes());
    }
}
