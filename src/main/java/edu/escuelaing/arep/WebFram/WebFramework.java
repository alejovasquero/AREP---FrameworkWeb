package edu.escuelaing.arep.WebFram;

import edu.escuelaing.arep.Handlers.HTMLHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class WebFramework {
    private static String HTML_HEADERS = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";


    private static String CSS_HEADERS = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/css\r\n"
            + "\r\n";


    public static String JPG_HEADERS = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: image/png\r\n"
            + "\r\n";


    private static String homeFolder = null;

    private static HashMap<String, BiFunction<String, Map<String, String>, String>> functions =
            new HashMap<String, BiFunction<String, Map<String, String>, String>>();


    public static void setHomeFolder(String path){
        homeFolder = path;
    }


    public static void main(String[] args){
        get("/index", (path, params) -> getHTML(path, params));
        System.out.println(getResource("/index", null));
    }

    public static void get(String path, BiFunction<String, Map<String, String>, String> function){
        functions.put(path, function);
    }

    public static String getResource(String path, Map<String, String> map) throws NoSuchElementException{
        if(functions.containsKey(path)){
            return functions.get(path).apply(path, map);
        } else {
            throw new NoSuchElementException("The element is not in the functions scope");
        }

    }

    public static String getHTML(String path, Map<String, String> map){
        String totalPath = getPath(path);
        HTMLHandler handler = new HTMLHandler(totalPath);
        return HTML_HEADERS + handler.getData();
    }


    public static String getCss(String path){
        String totalPath = getPath(path);
        HTMLHandler handler = new HTMLHandler(totalPath);
        return CSS_HEADERS + handler.getData();
    }


    public static Object[] getFile(String path){
        try {
            File myFile = new File(getPath(path));
            byte[] mybytearray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Object[] ans = {mybytearray,0,mybytearray.length};
            return ans;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }



    private static String getPath(String path){
        return homeFolder==null?path: homeFolder + path;
    }
}
