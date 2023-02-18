package com.magikcoco.game.engine;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Space {
    //TODO: Create class Space which represents a single unit area of the game board
    //TODO: an arraylist of Characters
    //TODO: an arraylist of TerrainFeatures
    //TODO: an arraylist of Conditions
    //TODO: an arraylist of Transports
    private List<Integer> coordinates;

    public Space setCoordinates(int[] coords){
        coordinates = new ArrayList();
        for(int i : coords){
            coordinates.add(i);
        }
        return this;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("coordinates", coordinates);
        return document;
    }
}
