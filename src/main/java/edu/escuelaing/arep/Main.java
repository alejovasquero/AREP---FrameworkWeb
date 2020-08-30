package edu.escuelaing.arep;

import edu.escuelaing.arep.HttpServer.HttpServer;
import edu.escuelaing.arep.WebFram.WebFramework;

import javax.imageio.stream.ImageInputStream;

import static edu.escuelaing.arep.WebFram.WebFramework.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        WebFramework.setHomeFolder("src/main/webapp");
        HttpServer.setPort(getPort());
        get("/", (path, params) -> WebFramework.getHTML("/data.html", params));
        get("/data.html", (path, params) -> WebFramework.getHTML(path, params));
        get("/css/style.css", (path, params) -> WebFramework.getCss(path));
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
