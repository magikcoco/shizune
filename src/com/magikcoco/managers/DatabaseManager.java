package com.magikcoco.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DatabaseManager {
    //TODO: create a method for getting connection

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static Map<String, MongoCollection<Document>> collections;

    private DatabaseManager(){
        //default constructor
        collections = new HashMap<>();
        LoggingManager.logInfo("DatabaseManager started");
    }

    public static DatabaseManager start(String connectionString, String databaseName) throws IllegalArgumentException{
        //TODO: case where connection fails
        mongoClient = new MongoClient(new MongoClientURI(connectionString));
        mongoDatabase = mongoClient.getDatabase(databaseName);
        for(Iterator i = mongoDatabase.listCollections().iterator(); i.hasNext();){
            Object next = i.next();
            if(next.getClass().toString().equals(MongoCollection.class.toString())){
                collections.put((((MongoCollection<Document>) next).getNamespace().getCollectionName()),(MongoCollection<Document>) next);
                LoggingManager.logInfo(((MongoCollection<Document>) next).getNamespace().getCollectionName() + " (MongoDB collection) was found");
            }
        }
        return INSTANCE;
    }
}
