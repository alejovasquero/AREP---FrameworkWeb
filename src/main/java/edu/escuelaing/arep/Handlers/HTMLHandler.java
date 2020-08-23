package edu.escuelaing.arep.Handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Alejandro Vasquez
 */
public class HTMLHandler {
    private String data;
    private String file;
    private boolean opened;

    public HTMLHandler(String file){
        this.file = file;
        data = getHTML(file);
    }

    /**
     * Cambia el archivo especificado
     * @param file Nuevo nombre del archivo
     */
    public void changeFile(String file){
        this.file = file;
        data = getHTML(file);
    }

    /**
     * Reemplaza las palabras en el texto que estaba en el archivo
     * @param bef Palabra por buscar
     * @param aft Palabra de reemplazo
     */
    public void replace(String bef, String aft){
        if(data!=null){
            data = data.replaceAll(bef, aft);
        }
    }

    /**
     * Retorna el texto del archivo
     * @return
     */
    public String getData(){
        if(opened){
            return data;
        }
        return null;
    }

    /**
     * Retorna un texto en un archivo especificado
     * @param file Archivo donde buscar
     * @return Texto almacenado en el archivo
     */
    private String getHTML(String file){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = contentBuilder.toString();
        opened = true;
        return content;
    }

}
