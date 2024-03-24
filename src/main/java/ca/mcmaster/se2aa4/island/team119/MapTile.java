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

    public Boolean sameTileType(MapTile tileToCompare) {
        return this.tileType == tileToCompare.tileType;
    }

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

