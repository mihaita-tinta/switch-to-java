package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

    public static void main2(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(9090, 100, InetAddress.getByName("localhost"));
        log.info("Server started  at:  " + serverSocket);
        while (true) {
            log.info("Waiting for a  connection...");

            final Socket activeSocket = serverSocket.accept();

            log.info("Received a  connection from  " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
        // FIXME how can we store a list of connected clients?
    }


    public static void handleClientRequest(Socket socket) {
        try {

            // FIXME any InputStream/OutputStream needs to be closed!
            BufferedReader socketReader = null;
            BufferedWriter socketWriter = null;
            socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            socketWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));

            String inMsg = null;
            while ((inMsg = socketReader.readLine()) != null) {
                log.info("Received from  client: " + inMsg);
                // FIXME what design pattern we need to implement so that we respond differently depending on the mesage?

                // if message has the format: "lowercase:Test 123" the server responds with "test 123"
                // if the message has the format: "uppercase:Test 123" the server responds with "TEST 123"
                // if the message has the format: "now:UTC" the server responds with "2018-10-11T12:26:14.937"
                //                              see ZoneId.getAvailableZoneIds()
                // if the message has the format: "path:<absolute_path>" list all filenames, otherwise print content of the file
                //                              Can you reuse some classes you created in your homework?
                String outMsg = inMsg;
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
            }
            socket.close();
        } catch(Exception e){
            // FIXME what happens if we just print the error?
            e.printStackTrace();
        }

    }
}
