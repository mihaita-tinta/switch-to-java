package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientApp {
    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

    public static void main(String[] args) throws Exception {

        // FIXME we need to retry connecting at least 3 times before giving up
        Socket socket = new Socket("localhost", 9090);
        log.info("Started client  socket at " + socket.getLocalSocketAddress());

        // FIXME streams needs to be closed
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));
        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in));

        String outMsg = null;

        log.info("Please enter a  message  (Bye  to quit):");
        while ((outMsg = consoleReader.readLine()) != null) {
            if (outMsg.equalsIgnoreCase("bye")) {
                break;
            }
            // Add a new line to the message to the http,
            // because the http reads one line at a time.
            socketWriter.write(outMsg);
            socketWriter.write("\n");
            socketWriter.flush();

            // Read and display the message from the http
            String inMsg = socketReader.readLine();
            log.info("Server: " + inMsg);
            log.info(""); // Print a blank line
            log.info("Please enter a  message  (Bye  to quit):");

            // FIXME after implementing the message commands on server, try validating the messages here before sending
        }
        socket.close(); // FIXME is this ok here?
    }
}
