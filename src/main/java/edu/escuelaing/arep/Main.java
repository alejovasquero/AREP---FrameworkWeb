package edu.escuelaing.arep;

import edu.escuelaing.arep.httpserver.HttpServer;
import edu.escuelaing.arep.services.MateriasServices;

import static edu.escuelaing.arep.webfram.WebFramework.*;

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

    private static void setFiles(){
        get("/", (path, params) -> getDBData());
        get("/data.html", (path, params) -> getPlain(path, params));
        get("/css/style.css", (path, params) -> getPlain(path, params));
        get("/css/styledb.css", (path, params) -> getPlain(path, params));
        get("/results", (path, params) -> getPlain("/results.html", params));
        get("/js/box.js", (path, params) -> getPlain(path, params));
    }

    private static void setImages(){
        getImage("/images/prueba.png", (path) -> getImage(path));
        getImage("/favicon.ico", (path) -> getImage(path));
        getImage("/images/img-portada.jpeg", (path) -> getImage(path));
    }

    public static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    public static String getDBData(){
        String a = getPlain("/database.html", null);
        return a.replace("reemplazo", service.getMateriasHTML());
    }
}
