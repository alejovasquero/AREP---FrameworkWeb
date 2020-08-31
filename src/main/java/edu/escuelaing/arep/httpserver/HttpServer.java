package edu.escuelaing.arep.httpserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import static edu.escuelaing.arep.webfram.WebFramework.*;
import static edu.escuelaing.arep.httpserver.headers.Headers.*;

public class HttpServer {
    private static boolean runnning = false;
    private static int port = 35000;

    public static void start() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+ port +".");
            System.exit(0);
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
            processClient(clientSocket);
        }
        serverSocket.close();
    }


    private static void processClient(Socket s){
        try {
            readRequest(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){

        }
    }
    public static void readRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        String[] argu = null;
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje: " + inputLine);
            argu = inputLine.split("\\s+");
            a.put(argu[0], argu);
            if (!in.ready()) {
                break;
            }
        }
        Request r = new Request(a);
        if(isSupported(r)){
            if(r.containsAccept()){
                findResponse(clientSocket, r);
            }
        } else {
            PrintWriter e = new PrintWriter(clientSocket.getOutputStream());
            e.println(NOT_FOUND);
            e.close();
        }
        in.close();
        clientSocket.close();
    }

    public static void findResponse(Socket clientSocket, Request request) throws IOException, NoSuchElementException {
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        if(!request.isImage()){
            out.writeBytes(headers.get(request.getResponse()));
            out.writeBytes(getResource(request.getResource(), null));
        } else {
            out.writeBytes(imageHeaders.get(request.getResponse()));
            byte[] image = getImageResource(request.getResource());
            out.write(image,0, image.length);
        }
        out.close();
    }


    public static void setPort(int p){
        port = p;
    }

}
