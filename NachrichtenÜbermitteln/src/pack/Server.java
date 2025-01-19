package pack;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;  // Port 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("verbindet...");
            
            
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client verbunden: " + clientSocket.getInetAddress());

            // Eingabe und Ausgabestreams
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            
            // Thread für Clientnachrichten
            Thread reader = new Thread(() -> {
                String message;
                try {
                    while ((message = input.readLine()) != null) {
                        System.out.println("Client: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reader.start();

            // Nachricht senden
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = consoleReader.readLine();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                output.println(message); //senden
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}