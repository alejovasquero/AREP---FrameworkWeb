package edu.escuelaing.arep.dao.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.entities.Materia;
import org.bson.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayList;
import java.util.List;

public class MongoDAO {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();


    public MongoClientURI uri;
    public MongoClient mongoClient;
    public MongoDatabase db;
    public MongoCollection<Document> coll;

    public MongoDAO(){
        uri = new MongoClientURI(
                "mongodb+srv://arep:AAIKUvaCY0KYPCKe@arep-cluster-server.re6r2.mongodb.net/AREPWEB?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("AREPWEB");
    }

    public  static void main(String[] args){
        MongoDAO mongoDAO = new MongoDAO();
        System.out.println(mongoDAO.getMaterias());
    }

    
    public List<Materia> getMaterias() {
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
            //...
        }
        cursor.close();
        return ans;
    }
}
