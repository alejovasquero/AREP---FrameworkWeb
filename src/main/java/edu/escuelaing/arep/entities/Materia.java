package edu.escuelaing.arep.entities;

/**
 * @author Alejandro Vasquez
 */
public class Materia {

    private String sigla;
    private int creditos;
    private String nombre;
    private String descripcion;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString(){
        return
                String.format("[ Sigla: %s, creditos: %d , Nombre: %s , Descripcion: %s ]",
                        getSigla(), getCreditos(), getNombre(), getDescripcion());
    }
}
