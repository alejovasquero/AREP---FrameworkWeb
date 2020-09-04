package edu.escuelaing.arep.webfram;

import edu.escuelaing.arep.handlers.HTMLHandler;
import edu.escuelaing.arep.httpserver.Request;
import static edu.escuelaing.arep.httpserver.headers.Headers.imageHeaders;
import static edu.escuelaing.arep.httpserver.headers.Headers.headers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    /**
     * Configura el folder de arranque para la busqueda de todos los archivos
     * @param path Ruta relatica de inicio (El final de la ruta debe estar sin /)
     */
    public static void setHomeFolder(String path){
        homeFolder = path;
    }

    /**
     * Mapea una funcion de retorno de imagenes con una ruta
     * @param path Ruta de mapeo
     * @param function Funcion a mapear con la ruta
     */
    public static void getImage(String path, Function<String, byte[]> function){
        functionsImages.put(path, function);
    }

    /**
     * Mapea una funcion con una ruta de un archivo plano
     * @param path Ruta del archivo plano
     * @param function Funcion a mapear
     */
    public static void get(String path, BiFunction<String, Map<String, String>, String> function){
        functions.put(path, function);
    }

    /**
     * Retorna el resultado de una funcion mapeada con una ruta
     * @param path Ruta mapeada
     * @param map Mapa con los parametros de la peticion
     * @return Texto HTML con la peticion
     * @throws NoSuchElementException El elemento mapeado con la ruta no existe
     */
    public static String getResource(String path, Map<String, String> map) throws NoSuchElementException{
        if(functions.containsKey(path)){
            return functions.get(path).apply(path, map);
        } else{
            throw new NoSuchElementException("The element is not in the functions scope");
        }
    }

    /**
     * Retorna una imagen en bytes mapeda con una ruta
     * @param path Ruta de mapeo
     * @return Arreglo de bytes de la imagen
     * @throws NoSuchElementException La imagen especificada con la ruta no existe
     */
    public static byte[] getImageResource(String path) throws NoSuchElementException {
        if(functionsImages.containsKey(path)){
            return functionsImages.get(path).apply(path);
        } else{
            throw new NoSuchElementException("The element is not in the functions scope");
        }
    }

    /**
     * Retorna un archivo plano con la ruta especificada
     * @param path Ruta para el acceso
     * @param map Parametros de la peticion
     * @return Texto plano que contiene el archivo
     */
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

    /**
     * Retorna una imagen mapeada con una ruta especificada
     * @param path Ruta de la imagen
     * @return Imagen en bytes de la ruta
     */
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

    /**
     * Retorna una ruta completo, resultado de la ruta de inicio especificada y otra dada
     * @param path Ruta dada
     * @return Ruta comleta del archivo
     */
    public static String getPath(String path){
        return homeFolder==null?path: homeFolder + path;
    }

    /**
     * Indica si la peticion puede ser procesada con la capacidades del sistema
     * @param r Peticon del cliente
     * @return Si es posible procesar la peticion
     */
    public static boolean isSupported(Request r){
        return (functionsImages.containsKey(r.getResource()) || functions.containsKey(r.getResource()))
                && (headers.containsKey(r.getResponse()) || imageHeaders.containsKey(r.getResponse()));
    }

}
