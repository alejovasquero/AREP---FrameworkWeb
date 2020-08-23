package edu.escuelaing.arep;

import edu.escuelaing.arep.HttpServer.HttpServer;
import edu.escuelaing.arep.WebFram.WebFramework;
import static edu.escuelaing.arep.WebFram.WebFramework.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        WebFramework.setHomeFolder("src/main/webapp");
        HttpServer.setPort(getPort());
        get("/data.html", (path, params) -> WebFramework.getHTML(path, params));
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
