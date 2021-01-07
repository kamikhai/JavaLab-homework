package driver;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("buyer");

        //сохранение
        Document doc = new Document("name", "Pasha")
                .append("surname", "Petrov")
                .append("email", "pasha@gmail.com")
                .append("password", "123");
        collection.insertOne(doc);

        //обновление
        collection.updateOne(eq("email", "pasha@gmail.com"), new Document("$set", new Document("email", "pasha123@gmail.com")));

        //удаление
        collection.deleteOne(eq("email", "pasha123@gmail.com"));



//        collection.find().forEach(document -> System.out.println(document.getString("name")));

        //db.goods.find({$or: [ {name: 'Jacket'}, {cost: {$lt: 1300}}]});

//        Document searchQuery = new Document();
//
//        searchQuery
//                .append("$or", Arrays.asList(
//                        new Document("name", "Jacket"),
//                        new Document("cost", new Document("$lt", 1300))));

//        FindIterable<Document> resultDocuments = collection.find(searchQuery);


//        FindIterable<Document> resultDocuments = collection.find(searchQuery)
//                .projection(new Document("name", 1)
//                        .append("cost", 1)
//                        .append("shop", 1)
//                        .append("_id", 0));

//        FindIterable<Document> resultDocuments = collection.find(
//                    or(new Document("name", "Jacket"),
//                            lt("cost", 1300)));

//        FindIterable<Document> resultDocuments = collection.find(searchQuery)
//                .projection(fields(include("name", "cost"), excludeId()));
//
//        for (Document document : resultDocuments) {
//            System.out.println(document.toJson());
//        }
    }
}
