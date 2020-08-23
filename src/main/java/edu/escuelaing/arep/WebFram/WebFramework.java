package edu.escuelaing.arep.WebFram;

import edu.escuelaing.arep.Handlers.HTMLHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class WebFramework {

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
        String totalPath = homeFolder==null?path: homeFolder + path;
        HTMLHandler handler = new HTMLHandler(totalPath);
        return handler.getData();
    }
}
