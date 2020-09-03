package edu.escuelaing.arep.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Alejandro Vasquez
 */
public class HTMLHandler {
    private String data;
    private String pointerFile;
    private boolean opened;

    public HTMLHandler(String file) throws IOException {
        this.pointerFile = file;
        data = getHTML(file);
    }

    /**
     * Cambia el archivo especificado
     * @param file Nuevo nombre del archivo
     */
    public void changeFile(String file) throws IOException {
        this.pointerFile = file;
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
    private String getHTML(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        }
        String content = contentBuilder.toString();
        opened = true;
        return content;
    }

}
