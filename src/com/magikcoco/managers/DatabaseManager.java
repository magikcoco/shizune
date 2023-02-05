package com.magikcoco.managers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


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

    public static DatabaseManager addDocument(Map<String, Object> map){
        Document document = new Document(map);
        mongoDatabase.getCollection("ActiveThreads").insertOne(document);
        return INSTANCE;
    }

    public static DatabaseManager endConnection(){
        mongoClient.close();
        return INSTANCE;
    }
}
