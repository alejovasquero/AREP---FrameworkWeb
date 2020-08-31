package edu.escuelaing.arep.services;

import edu.escuelaing.arep.dao.mongodb.MongoDAO;
import edu.escuelaing.arep.entities.Materia;

/**
 * @author Alejandro Vasquez
 */
public class MateriasServices {

    private MongoDAO mongoDAO;

    public MateriasServices(){
        mongoDAO = new MongoDAO();
    }

    /**
     * Retorna las materias de la base de datos en un formato de tabla
     * @return HTML con la informacion de las materias
     */
    public String getMateriasHTML(){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for(Materia m: mongoDAO.getMaterias()){
                stringBuilder.append(String.format("<tr>\n" +
                        "        <td>%s</td>\n" +
                        "        <td>%d</td>\n" +
                        "        <td>%s</td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>", m.getSigla(), m.getCreditos(), m.getNombre(), m.getDescripcion()));
            }
            return stringBuilder.toString();
        } catch (Exception e ){
            return "Error en la base de datos :(";
        }
    }
}
