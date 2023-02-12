package com.magikcoco.game.engine;

import java.util.Random;

public class GameEngine {
    //TODO: create a method that randomly generates an entire map of spaces with given dimensions and saves it in a database
    //TODO: create a method that creates a space with a specific set of information, overloaded for level of detail

    int[] dimensions;

    public GameEngine(int[] dimensions){
        this.dimensions = dimensions;
    }

    public int[] generateRandomCoords(){
        int[] coords = new int[dimensions.length];
        for(int i = 0; i < dimensions.length; i++){
            Random random = new Random();
            coords[i] = random.nextInt(dimensions[i]);
        }
        return coords;
    }

}
