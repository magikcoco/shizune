package com.magikcoco.game.data;

import com.magikcoco.game.engine.Space;
import com.magikcoco.managers.DatabaseManager;
import java.util.HashMap;
import java.util.Map;

public class LeavingEarth {
    //dimensions for game engine coordinate grid
    public static int Xdim = 9;
    public static int Ydim = 6;

    public static void buildMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("Game", "Leaving Earth");
        map.put("Map Name", "Leaving Earth Standard Board");
        map.put("Solar Radiation", (new Space()).setCoordinates(new int[]{0,0}).toDocument());
        map.put("Earth", (new Space()).setCoordinates(new int[]{0,2}).toDocument());
        map.put("Lunar FlyBy", (new Space()).setCoordinates(new int[]{1,1}).toDocument());
        map.put("Suborbital Flight", (new Space()).setCoordinates(new int[]{1,2}).toDocument());
        map.put("Mercury", (new Space()).setCoordinates(new int[]{1,3}).toDocument());
        map.put("Moon", (new Space()).setCoordinates(new int[]{2,0}).toDocument());
        map.put("Lunar Orbit", (new Space()).setCoordinates(new int[]{2,1}).toDocument());
        map.put("Earth Orbit", (new Space()).setCoordinates(new int[]{2,2}).toDocument());
        map.put("Mercury Orbit", (new Space()).setCoordinates(new int[]{2,3}).toDocument());
        map.put("Mercury FlyBy", (new Space()).setCoordinates(new int[]{2,4}).toDocument());
        map.put("Venus FlyBy", (new Space()).setCoordinates(new int[]{3,1}).toDocument());
        map.put("Inner Planets Transfer", (new Space()).setCoordinates(new int[]{3,2}).toDocument());
        map.put("Mars Orbit", (new Space()).setCoordinates(new int[]{3,3}).toDocument());
        map.put("Mars", (new Space()).setCoordinates(new int[]{3,4}).toDocument());
        map.put("Venus", (new Space()).setCoordinates(new int[]{4,0}).toDocument());
        map.put("Venus Orbit", (new Space()).setCoordinates(new int[]{4,1}).toDocument());
        map.put("Ceres", (new Space()).setCoordinates(new int[]{4,2}).toDocument());
        map.put("Mars FlyBy", (new Space()).setCoordinates(new int[]{4,3}).toDocument());
        map.put("Phobos", (new Space()).setCoordinates(new int[]{4,4}).toDocument());
        map.put("Outer Planets Transfer", (new Space()).setCoordinates(new int[]{5,2}).toDocument());
        map.put("Jupiter FlyBy", (new Space()).setCoordinates(new int[]{5,3}).toDocument());
        map.put("Ganymede", (new Space()).setCoordinates(new int[]{5,4}).toDocument());
        map.put("Titan", (new Space()).setCoordinates(new int[]{6,0}).toDocument());
        map.put("Titan Orbit", (new Space()).setCoordinates(new int[]{6,1}).toDocument());
        map.put("Saturn FlyBy", (new Space()).setCoordinates(new int[]{6,2}).toDocument());
        map.put("Jupiter Orbit", (new Space()).setCoordinates(new int[]{6,3}).toDocument());
        map.put("Ganymede Orbit", (new Space()).setCoordinates(new int[]{6,4}).toDocument());
        map.put("Io", (new Space()).setCoordinates(new int[]{6,5}).toDocument());
        map.put("Enceladus", (new Space()).setCoordinates(new int[]{7,0}).toDocument());
        map.put("Saturn Orbit", (new Space()).setCoordinates(new int[]{7,1}).toDocument());
        map.put("Callisto", (new Space()).setCoordinates(new int[]{7,3}).toDocument());
        map.put("Europa", (new Space()).setCoordinates(new int[]{7,4}).toDocument());
        map.put("Jupiter", (new Space()).setCoordinates(new int[]{7,5}).toDocument());
        map.put("Saturn", (new Space()).setCoordinates(new int[]{8,0}).toDocument());
        map.put("Uranus", (new Space()).setCoordinates(new int[]{8,1}).toDocument());
        map.put("Uranus FlyBy", (new Space()).setCoordinates(new int[]{8,2}).toDocument());
        map.put("Neptune FlyBy", (new Space()).setCoordinates(new int[]{8,3}).toDocument());
        map.put("Neptune", (new Space()).setCoordinates(new int[]{8,4}).toDocument());
        DatabaseManager.addDocumentToGameMaps(map);
    }
}
