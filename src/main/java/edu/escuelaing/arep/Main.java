package edu.escuelaing.arep;

import edu.escuelaing.arep.httpserver.HttpServer;
import edu.escuelaing.arep.services.MateriasServices;
import static edu.escuelaing.arep.webfram.WebFramework.setHomeFolder;
import static edu.escuelaing.arep.webfram.WebFramework.get;
import static edu.escuelaing.arep.webfram.WebFramework.getPlain;
import static edu.escuelaing.arep.webfram.WebFramework.getImage;
import java.io.IOException;

public class Main {

    private static MateriasServices service = new MateriasServices();

    public static void main(String[] args){
        setHomeFolder("src/main/webapp");
        HttpServer.setPort(getPort());
        setFiles();
        setImages();
        try {
            HttpServer.start();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Configura todas las rutas a los archivos de texto plano, mapenado con rutas de acceso
     */
    private static void setFiles(){
        get("/", (path, params) -> getDBData());
        get("/data.html", (path, params) -> getPlain(path, params));
        get("/css/style.css", (path, params) -> getPlain(path, params));
        get("/css/styledb.css", (path, params) -> getPlain(path, params));
        get("/results", (path, params) -> getPlain("/results.html", params));
        get("/js/box.js", (path, params) -> getPlain(path, params));
    }

    /**
     * Configura todas la imagenes, mapeando con rutas de acceso
     */
    private static void setImages(){
        getImage("/images/prueba.png", (path) -> getImage(path));
        getImage("/favicon.ico", (path) -> getImage(path));
        getImage("/images/img-portada.jpeg", (path) -> getImage(path));
    }

    /**
     * Retorna el puerto del ambiente
     * @return Retorna el puerto configurado en el servidor, si hay un configurado, de lo contrario toma como defecto el 36000
     */
    public static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    /**
     * Retorna un archivo HTML configurado y mapeado con una ruta fija a un HTML
     * @return HTML con tablas e informacion de las materias en la base de datos
     */
    public static String getDBData(){
        String a = getPlain("/database.html", null);
        return a.replace("reemplazo", service.getMateriasHTML());
    }
}
