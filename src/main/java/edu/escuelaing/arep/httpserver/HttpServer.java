package edu.escuelaing.arep.httpserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import static edu.escuelaing.arep.webfram.WebFramework.*;

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
            makeResponse(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){

        }
    }
    public static void makeResponse(Socket clientSocket) throws IOException {
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
        findResponse(clientSocket, a);
        in.close();
        clientSocket.close();
    }

    public static void findResponse(Socket clientSocket, HashMap<String, String[]> request) throws IOException, NoSuchElementException {

        String outputLine = null;

        String type = request.get("Accept:")[1];
        System.out.println("HELLO: "+type);
        if(type.contains("text/html") || type.contains("text/css")){
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            outputLine = getResource(request.get("GET")[1], null);
            out.println(outputLine);
            out.close();
        } else {
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            File file = new File("src/main/webapp/images/prueba.png");
            int numOfBytes = (int) file.length();

            FileInputStream inFile  = new FileInputStream ("src/main/webapp/images/prueba.png");

            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            out.writeBytes(JPG_HEADERS);
            out.write(fileInBytes,0, numOfBytes);
            out.close();
            inFile.close();
        }
        System.out.println(outputLine);

    }


    public static void setPort(int p){
        port = p;
    }

}
