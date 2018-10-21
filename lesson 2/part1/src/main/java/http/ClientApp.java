package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;

public class ClientApp {
    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

    private static final int maxNoOfTries = 3;
    private static Socket socket;

    private static void initSocket() {
        int noOfTries = 0;

        while (socket==null && noOfTries < maxNoOfTries) {
            try {
                if (noOfTries > 0) {
                    Thread.sleep(2000L);
                }
                socket = new Socket("localhost", 9090);
                log.info("Started client  socket at " + socket.getLocalSocketAddress());
            } catch (ConnectException e) {
                System.out.println("Error::" + e.getClass() + "; Message::" + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error::" + e.getClass() + "; Message::" + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error::" + e.getClass() + "; Message::" + e.getMessage());
            } finally {
                noOfTries++;
                System.out.println("Number of tries: " + noOfTries);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        // FIXME we need to retry connecting at least 3 times before giving up
        initSocket();

        if (socket!=null && socket.isConnected()) {
            // FIXME streams needs to be closed
            BufferedReader socketReader     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter     = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader consoleReader    = new BufferedReader(new InputStreamReader(System.in));

            String outMsg = null;

            try {
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
            } catch (Exception e) {
                System.out.println("Error::" + e.getClass() + "; Message::" + e.getMessage());
            } finally {
                System.out.println("Close all stuff here");
                socketReader.close();
                socketWriter.close();
                consoleReader.close();
                socket.close(); // FIXME is this ok here?
            }
        }
    }
}
