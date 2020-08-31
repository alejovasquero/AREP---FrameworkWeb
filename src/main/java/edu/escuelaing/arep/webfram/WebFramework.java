package edu.escuelaing.arep.webfram;

import edu.escuelaing.arep.handlers.HTMLHandler;
import edu.escuelaing.arep.httpserver.Request;
import static edu.escuelaing.arep.httpserver.headers.Headers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WebFramework {



    private static String homeFolder = null;

    private static HashMap<String, BiFunction<String, Map<String, String>, String>> functions =
            new HashMap<String, BiFunction<String, Map<String, String>, String>>();

    private static HashMap<String, Function<String, byte[]>> functionsImages =
            new HashMap<String, Function<String, byte[]>>();


    public static void setHomeFolder(String path){
        homeFolder = path;
    }

    public static void getImage(String path, Function<String, byte[]> function){
        functionsImages.put(path, function);
    }

    public static void get(String path, BiFunction<String, Map<String, String>, String> function){
        functions.put(path, function);
    }

    public static String getResource(String path, Map<String, String> map) throws NoSuchElementException{
        if(functions.containsKey(path)){
            return functions.get(path).apply(path, map);
        } else{
            throw new NoSuchElementException("The element is not in the functions scope");
        }
    }


    public static byte[] getImageResource(String path) throws NoSuchElementException {
        if(functionsImages.containsKey(path)){
            return functionsImages.get(path).apply(path);
        } else{
            throw new NoSuchElementException("The element is not in the functions scope");
        }
    }

    public static String getPlain(String path, Map<String, String> map){
        String totalPath = getPath(path);
        String ans = null;
        try{
            HTMLHandler handler = new HTMLHandler(totalPath);
            ans = handler.getData();
        } catch (IOException e){
            ans = "MAPEO DE ARCHIVO INEXISTENTE |:(";
        }
        return ans;
    }

    public static byte[] getImage(String path) {
        File file = new File(getPath(path));
        int numOfBytes = (int) file.length();
        FileInputStream inFile  = null;
        byte[] fileInBytes = new byte[numOfBytes];
        try {
            inFile = new FileInputStream(getPath(path));
            inFile.read(fileInBytes);
        } catch (IOException e) {
            //
        }
        return fileInBytes;
    }

    private static String getPath(String path){
        return homeFolder==null?path: homeFolder + path;
    }

    public static boolean isSupported(Request r){
        return (functionsImages.containsKey(r.getResource()) || functions.containsKey(r.getResource()))
                && (headers.containsKey(r.getResponse()) || imageHeaders.containsKey(r.getResponse()));
    }

}
