package bot.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class TCPServer {

    /**
     * Opening port 4444 Listening to the clients Updating bots configurations
     * from db on the clients command
     *
     * @param s Server Socket
     */
    static void launchServer(ServerSocket s) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket server = s) {
                    System.out.println("Listening on port 4444");
                    //Server keeps listening after a closed connection
                    while (true) {
                        try (Socket clientSocket = server.accept()) {
                            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {
                                try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                                    String inputLine;
                                    p("*** New connection on port 4444 ***");
                                    p("-----------------------------------");
                                    while (!(inputLine = br.readLine()).equals("bye")) {
                                        p("-->New message from the client:");

                                        //get new congig for the command
                                        if (inputLine.contains("MoodyBot")) {
                                            //identifying the right command
                                            String[] commandsAndArgs = inputLine.split(":");
                                            if (commandsAndArgs[1].equals("NewConfig")) {
                                                //waiting for the db to update to get fresg data
                                                Thread.sleep(1000);
                                                getNewConfiguration();

                                                p("Moody Bot has new configuration"); //console message

                                                //Server response message
                                                out.println("Moody Bot: I recieved the message about the new configuration.");
                                                out.flush();
                                            } else {
                                                //Server response message if the command is unknown
                                                out.println("Moody Bot: I do not recognize your command.");
                                                out.flush();
                                            }
                                        }
                                    }
                                    p("--------------------------------------------------");
                                    p("*** Connection with the Client has been closed ***");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        thread.start();
    }

    /**
     * Assigning new configuration to our bot from db
     */
    private static void getNewConfiguration() {
        MoodyBotApp.mb = MoodyBotApp.db.getConfigurations();
    }

    static void p(String msg) {
        System.out.println(msg);
    }
}
