// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// MapTile
// holds tileType of tile on Map

package ca.mcmaster.se2aa4.island.team119;

import java.util.ArrayList;

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

    // checks if the tile is of the same Type(enum) as the one it is being compared to
    public Boolean sameTileType(MapTile tileToCompare) {
        return this.tileType == tileToCompare.tileType;
    }

    // converts the string tileType to an element of Type enum
    // returns the tile as Type
    // parameters - the tileType as a string
    private Type toTile(String tileType) {
        switch (tileType.toLowerCase()) {
            case "ground", "beach", "grassland" -> {
                return Type.LAND;
            }
            case "lake" -> {
                return Type.LAKE;
            }
            case "ocean", "out_of_range" -> {
                return Type.OCEAN;
            }
            default -> {
                return Type.UNKNOWN;
            }
        }
    }

    // converts the arrayList of strings of tileTypes to an element of Type enum
    // returns the tile as Type
    // parameters - the tileTypes as an arrayList of strings
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

