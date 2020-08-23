package edu.escuelaing.arep.HttpServer;

import edu.escuelaing.arep.WebFram.WebFramework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static edu.escuelaing.arep.WebFram.WebFramework.*;

public class HttpServer {
    private static boolean runnning = false;
    private static int port = 35000;

    public static void start() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+ port +".");
            System.exit(1);
            }
        Socket clientSocket = null;
        runnning = true;
        while(runnning) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            makeResponse(clientSocket);
        }
        serverSocket.close();
    }

    public static void makeResponse(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        outputLine += WebFramework.getResource("/data.html", null);
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
    }

    public static void setPort(int p){
        port = p;
    }

}
