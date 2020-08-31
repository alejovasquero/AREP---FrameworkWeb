package edu.escuelaing.arep.httpserver.headers;

import java.util.HashMap;

/**
 * @author Alejandro Vasquez
 */
public class Headers {

    public static HashMap<String, String> headers = new HashMap<String, String>();
    public static HashMap<String, String> imageHeaders = new HashMap<String, String>();

    static {
        headers.put("text/html", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n");

        headers.put("text/css", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/css\r\n"
                + "\r\n");

        headers.put("js", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/javascript\r\n"
                + "\r\n");

        imageHeaders.put("jpg", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/jpg\r\n"
                + "\r\n");

        imageHeaders.put("png", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/png\r\n"
                + "\r\n");

        imageHeaders.put("ico", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/x-icon\r\n"
                + "\r\n");

        imageHeaders.put("jpeg", "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/jpeg\r\n"
                + "\r\n");
    }

    public static String NOT_FOUND = "HTTP/1.1 404 Not Found";
}
