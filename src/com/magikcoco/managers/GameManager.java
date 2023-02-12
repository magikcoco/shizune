package com.magikcoco.managers;

import com.magikcoco.game.engine.GameEngine;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    //TODO: Create a GameManager
    //TODO: map ThreadHandler -> GameEngine

    private static Map<String, GameEngine> threadIdGameMap = new HashMap<>();

    public static void startGameInThread(String threadId, GameEngine gameEngine){
        threadIdGameMap.put(threadId, gameEngine);
    }
}
