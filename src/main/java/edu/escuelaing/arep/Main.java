package edu.escuelaing.arep;

import edu.escuelaing.arep.httpserver.HttpServer;
import edu.escuelaing.arep.webfram.WebFramework;

import static edu.escuelaing.arep.webfram.WebFramework.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        setHomeFolder("src/main/webapp");
        HttpServer.setPort(getPort());
        get("/", (path, params) -> getHTML("/data.html", params));
        get("/data.html", (path, params) -> getHTML(path, params));
        get("/css/style.css", (path, params) -> getCss(path));
        try {
            HttpServer.start();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
