package edu.escuelaing.arep.httpserver;

import edu.escuelaing.arep.httpserver.headers.Headers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class Request {

    private URL url;
    private String httpVersion;
    private String method;
    private String[] acceptHeaders;
    private String headerKey;

    public Request(Map<String, String[]> map){
        processMethod(map);
        processPath(map);
        processHttpVersion(map);
        processAccepHeaders(map);
    }

    private void processAccepHeaders(Map<String, String[]> map) {
        if(map.containsKey("Accept:")){
            acceptHeaders = map.get("Accept:")[1].split(";")[0].split("\\,");
            if(acceptHeaders.length!=0 && Headers.headers.containsKey(acceptHeaders[0])){
                headerKey = acceptHeaders[0];
            } else {
                String[] type = getResource().split("\\.");
                headerKey = type[type.length-1];

            }
        } else {
            acceptHeaders = null;
        }
    }

    private void processMethod(Map<String, String[]> map) {
        if(map.containsKey("GET")){
            method = map.get("GET")[0];
        } else {
            method = null;
        }
    }


    private void processHttpVersion(Map<String, String[]> map){
        if(map.containsKey("GET")){
            httpVersion = map.get("GET")[2];
        } else {
            httpVersion = null;
        }
    }

    private void processPath(Map<String, String[]> map){
        try {
            if(map.containsKey("GET")){
                System.out.println(Arrays.toString(map.get("GET")));
                url = new URL("http://localhost"+map.get("GET")[1]);
            } else {
                url = new URL("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getResource(){
        return url.getPath();
    }

    public boolean containsAccept(){
        return acceptHeaders != null;
    }


    public boolean isImage(){
        return !Headers.headers.containsKey(headerKey) && Headers.imageHeaders.containsKey(headerKey);
    }

    public String getResponse(){
        return headerKey;
    }
}
