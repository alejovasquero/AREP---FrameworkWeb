package edu.escuelaing.arep.httpserver;

import edu.escuelaing.arep.httpserver.headers.Headers;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author Alejandro Vasquez
 */
public class Request {

    private URL url;
    private String htVersion;
    private String htMethod;
    private String[] acceptHeaders;
    private String headerKey;

    public Request(Map<String, String[]> map){
        processMethod(map);
        processPath(map);
        processHttpVersion(map);
        processAccepHeaders(map);
    }

    /**
     * Mapea los posibles formatos de retorno para una respuesta
     * @param map Mapa con los encabezados de la peticion
     */
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

    /**
     * Mapea el método HTTP usado en la petición
     * @param map Mapa con los encabezados de la peticion
     */
    private void processMethod(Map<String, String[]> map) {
        if(map.containsKey("GET")){
            htMethod = map.get("GET")[0];
        } else {
            htMethod = null;
        }
    }

    /**
     * Mapea la version de HTTP de la peticion
     * @param map Mapa con los encabezados de la peticion
     */
    private void processHttpVersion(Map<String, String[]> map){
        if(map.containsKey("GET")){
            htVersion = map.get("GET")[2];
        } else {
            htVersion = null;
        }
    }

    /**
     * Mapea la URL de la petición, con el objetivo de consultar los recursos y parámetros
     * @param map Mapa con los encabezados de la peticion
     */
    private void processPath(Map<String, String[]> map){
        try {
            if(map.containsKey("GET")){
                String path = map.get("GET")[1];
                url = new URL("http://localhost"+ (path == null? "": path));
            } else {
                url = new URL("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna el recurso que se espeficia en URL de la peticion
     * @return Ruta del recurso de la petición, sin parametros
     */
    public String getResource(){
        return url.getPath();
    }

    /**
     * Indica si la peticion contiene formatos de aceptacion
     * @return Si en los encabezados habia indicacion ACCEPT
     */
    public boolean containsAccept(){
        return acceptHeaders != null;
    }

    /**
     * Si el recurso de la peticion es una images
     * @return Si el recurso especificado es una imagen, se limita a la revision de la clase Header
     */
    public boolean isImage(){
        return !Headers.headers.containsKey(headerKey) && Headers.imageHeaders.containsKey(headerKey);
    }

    /**
     * Retorna el formato que se debe especificar en los encabezados de retorno
     * @return Formato para el retorno. Será comparado con la clase Header
     */
    public String getResponse(){
        return headerKey;
    }
}
