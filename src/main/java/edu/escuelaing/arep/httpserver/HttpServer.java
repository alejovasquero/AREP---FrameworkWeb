package edu.escuelaing.arep.httpserver;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import static edu.escuelaing.arep.webfram.WebFramework.isSupported;
import static edu.escuelaing.arep.webfram.WebFramework.getResource;
import static edu.escuelaing.arep.webfram.WebFramework.getImageResource;
import static edu.escuelaing.arep.httpserver.headers.Headers.NOT_FOUND;
import static edu.escuelaing.arep.httpserver.headers.Headers.headers;
import static edu.escuelaing.arep.httpserver.headers.Headers.imageHeaders;

/**
 * @author Alejandro Vasquez
 */
public class HttpServer {
    private static boolean runnning = false;
    private static int port = 35000;

    /**
     * Arranca el servidor web en el puerto configurado
     * @throws IOException Excepcion de lectura y escritura sobre archivos
     */
    public static void start() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+ port +".");
        }
        Socket clientSocket = null;
        runnning = true;
        while(runnning) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
            processClient(clientSocket);
        }
        serverSocket.close();
    }

    /**
     * Inicia el proceso de procesamiento de la peticion
     * @param s Socket del cliente
     */
    private static void processClient(Socket s){
        try {
            readRequest(s);
        } catch (Exception es){
            PrintWriter e = null;
            try {
                e = new PrintWriter(s.getOutputStream());
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
            e.println(NOT_FOUND);
            e.close();
        }
    }

    /**
     * Lee las lineas de la peticion y las mapea
     * @param clientSocket Conexion del cliente
     * @throws IOException Excepcion de lectura y escritura de archivos
     */
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
        if (a.containsKey("Accept:")) {
            Request r = new Request(a);
            if (isSupported(r)) {
                if (r.containsAccept()) {
                    findResponse(clientSocket, r);
                }
            } else {
                PrintWriter e = new PrintWriter(clientSocket.getOutputStream());
                e.println(NOT_FOUND);
                e.close();
            }
        }
        in.close();
        clientSocket.close();
    }

    /**
     * Configura encapsulamiento de la respuesta con sus respectivos encabezados
     * @param clientSocket Conexion del cliente
     * @param request Peticion con los datos mapeados
     * @throws IOException Error buscando y escribiendo en archivos mapeados
     * @throws NoSuchElementException Los archivos configurados con el framework web no existen
     */
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
