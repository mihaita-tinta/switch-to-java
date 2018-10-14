package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientApp {
    private static final Logger log = LoggerFactory.getLogger(ClientApp.class);


    public static void main(String[] args) throws Exception {

        // FIXME we need to retry connecting at least 3 times before giving up
        int nrRetries = 1;
        Socket socket=null;

        while(nrRetries <= 3) {
            try {
                if(nrRetries != 1) {
                    Thread.sleep(1000);
                }
                socket = new Socket("localhost", 9090);
                log.info("Started client  socket at " + socket.getLocalSocketAddress());
                break;
            }catch(ConnectException e){
                System.out.println("Trying to connect to localhost : "+nrRetries);
                nrRetries++;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
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
