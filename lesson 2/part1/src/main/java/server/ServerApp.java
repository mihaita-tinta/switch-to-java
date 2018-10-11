package server;


import server.tcpserver.MessageHandler;
import server.tcpserver.NioServer;
import server.tcpserver.TcpServer;
import server.tcpserver.ThreadedServer;
import server.tcpserver.codec.CodecPipeline;
import server.tcpserver.codec.CodecPipelineFactory;
import server.tcpserver.codec.StringCodec;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class ServerApp {
    public static void main( String[] args ) throws IOException {

        TcpServer server = new ThreadedServer(8080, new CodecPipelineFactory() {
            @Override
            public CodecPipeline newCodecPipeline() {
                CodecPipeline pipeline = new CodecPipeline();
                pipeline.addCodec(new StringCodec());
                return  pipeline;
            }
        }, new MessageHandler<String, String>() {

            @Override
            public List<String> handle(String message) {
                return Collections.singletonList(message);
            }
        });

        server.start();

        Scanner stdin = new Scanner(System.in);
        System.out.println("Server started, press enter to exit");
        String line = stdin.nextLine();
        System.out.println("Stopping server...");
        server.stop();
    }
}
