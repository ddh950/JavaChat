package pack;

import java.io.*;
import java.net.*;

public class Client{
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server-Adresse
        int port = 12345;  // Port

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("verbunden");

            // Eingabe und Ausgabestreams
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Thread Nachrichten Server
            Thread reader = new Thread(() -> {
                String message;
                try {
                    while ((message = input.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reader.start();

            // Nachricht senden
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = console.readLine();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                output.println(message); // senden
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}