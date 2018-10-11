package server.tcpserver.codec;

import java.util.Iterator;
import java.util.LinkedList;

public class CodecPipeline {
    private LinkedList<Codec> codecs;

    public CodecPipeline() {
        codecs = new LinkedList<>();
    }

    public void addCodec(Codec codec) {
        codecs.add(codec);
    }

    public Iterator<Codec> getReadOrder() {
        return codecs.iterator();
    }

    public Iterator<Codec> getWriteOrder() {
        return codecs.descendingIterator();
    }
}
