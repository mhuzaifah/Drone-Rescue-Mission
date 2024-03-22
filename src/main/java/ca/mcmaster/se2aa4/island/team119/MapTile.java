package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.OptionalInt;

public class MapTile  {
    Type tileType;
    enum Type {
        LAND,
        OCEAN,
        LAKE,
        UNKNOWN
    }

    MapTile(String tile) {
        this.tileType = toTile(tile);
    }

    MapTile(ArrayList<String> tile) {
        this.tileType = toTile(tile);
    }

    public Boolean sameTileType(MapTile tileToCompare) {
        return this.tileType == tileToCompare.tileType;
    }

    private Type toTile(String tileType) {
        switch (tileType) {
            case "OCEAN", "OUT_OF_RANGE" -> { return Type.OCEAN; }
            case "UNKNOWN" -> { return Type.UNKNOWN; }
            case "LAKE" -> { return Type.LAKE; }
            default -> { return Type.LAND; }
        }
    }

    private Type toTile(ArrayList<String> biomes) {

        for(String biome : biomes) {
            if(biome.equals("OCEAN"))
                return Type.OCEAN;
            else if(biome.equals("LAKE"))
                return Type.LAKE;
        }
        return Type.LAND;
    }

}

