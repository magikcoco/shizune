package com.magikcoco.managers;

import com.magikcoco.game.engine.Space;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Filter;


public class DatabaseManager {

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static Map<String, MongoCollection<?>> collectionMap;

    private DatabaseManager(){
        //default constructor
        collectionMap = new HashMap<>();
        LoggingManager.logInfo("DatabaseManager started");
    }

    public static DatabaseManager start(String connectionString, String databaseName) throws IllegalArgumentException{
        mongoClient = MongoClients.create(connectionString);
        mongoDatabase = mongoClient.getDatabase(databaseName);
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        collectionNames.forEach((Consumer<? super String>) (String collectionName) -> {
            collectionMap.put(collectionName, mongoDatabase.getCollection(collectionName));
            LoggingManager.logInfo(collectionName + " (MongoDB collection) was found");
        });
        return INSTANCE;
    }

    public static DatabaseManager addDocumentToActiveThreads(Map<String, Object> map){
        Document document = new Document(map);
        mongoDatabase.getCollection("ActiveThreads").insertOne(document);
        return INSTANCE;
    }

    public static DatabaseManager appendToDocumentInActiveThreads(String threadId, String fieldName, Object value){
        MongoCollection<Document> collection = mongoDatabase.getCollection("ActiveThreads");
        Document document = collection.find(Filters.eq("thread-id",threadId)).first();
        if(document != null){
            collection.updateOne(Filters.eq("_id", document.get("_id")), Updates.set(fieldName,value));
        }
        return INSTANCE;
    }

    public static DatabaseManager removeDocumentFromActiveThreads(Map<String, Object> map){
        Document document = new Document(map);
        mongoDatabase.getCollection("ActiveThreads").deleteOne(document);
        return INSTANCE;
    }

    public static void addDocumentToGameMaps(Map<String, Object> map){
        Document document = new Document(map);
        if(mongoDatabase.getCollection("GameMaps").find(Filters.eq("Map Name", map.get("Map Name"))).first() == null){
            mongoDatabase.getCollection("GameMaps").insertOne(document);
        } else {
            LoggingManager.logWarning("Attempted to insert a map that already exists: " + map.get("Map Name"));
        }
    }

    public static DatabaseManager endConnection(){
        mongoClient.close();
        return INSTANCE;
    }
}
