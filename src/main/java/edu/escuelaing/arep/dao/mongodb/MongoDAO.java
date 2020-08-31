package edu.escuelaing.arep.dao.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.entities.Materia;
import edu.escuelaing.arep.services.exceptions.PersistenceException;
import org.bson.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alejandro Vasquez
 */
public class MongoDAO {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();


    public static MongoClientURI uri;
    public static MongoClient mongoClient;
    public static MongoDatabase db;
    public static MongoCollection<Document> coll;


    static {
        uri = new MongoClientURI(
                "mongodb+srv://arep:AAIKUvaCY0KYPCKe@arep-cluster-server.re6r2.mongodb.net/AREPWEB?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("AREPWEB");
    }


    /**
     * Retorna todas las materias que se encuentran en la base de datos
     * @return Lista de materias de la base de datos
     * @throws PersistenceException Error con la conexión a la base de datos
     */
    public List<Materia> getMaterias() throws PersistenceException {
        coll = db.getCollection("materias");
        MongoCursor<Document> cursor = coll.find().projection(excludeId()).iterator();
        ArrayList<Materia> ans= new ArrayList<Materia>();
        try {
            while (cursor.hasNext()) {
                Materia m = null;
                m = JSON_MAPPER.readValue(cursor.next().toJson(), Materia.class);
                ans.add(m);
            }
        } catch (JsonProcessingException e)  {
            throw new PersistenceException("Error en la base de datos");
        }
        cursor.close();
        return ans;
    }
}
