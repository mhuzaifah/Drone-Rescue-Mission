package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

public class MapTileTest {
/*
    @Test
    void testSameTileType() {
        MapTile tile1 = new MapTile("OCEAN");
        MapTile tile2 = new MapTile("OCEAN");
        MapTile tile3 = new MapTile("LAKE");
        MapTile tile4 = new MapTile("LAND");
        MapTile tile5 = new MapTile("UNKNOWN");

        // Test if tiles of the same type are considered the same
        assertEquals(true, tile1.sameTileType(tile2));
        // Test if tiles of different types are not considered the same
        assertEquals(false, tile1.sameTileType(tile3));
        // Test if tiles of different types that seem to be the same in the end are not considered the same
        assertEquals(false, tile4.sameTileType(tile5));
    }*/

    @Test
    void testToTileWithString() {
        // Test conversion from single string
        MapTile tile1 = new MapTile("OCEAN");
        assertEquals(MapTile.Type.OCEAN, tile1.tileType);

        MapTile tile2 = new MapTile("GROUND");
        assertEquals(MapTile.Type.LAND, tile2.tileType);

        MapTile tile3 = new MapTile("UNKNOWN");
        assertEquals(MapTile.Type.UNKNOWN, tile3.tileType);

        MapTile tile4 = new MapTile("OUT_OF_RANGE");
        assertEquals(MapTile.Type.OCEAN, tile4.tileType);

        MapTile tile5 = new MapTile("LAKE");
        assertEquals(MapTile.Type.LAKE, tile5.tileType);

        MapTile tile6 = new MapTile("BEACH");
        assertEquals(MapTile.Type.LAND, tile6.tileType);
    }

    @Test
    void testToTileWithArrayList() {
        // Test conversion from ArrayList<String>

        //what happens when there are no biomes in the list????

        // Test case where there's an ocean biome
        ArrayList<String> oceanBiomes = new ArrayList<>();
        oceanBiomes.add("OCEAN");
        oceanBiomes.add("GROUND");
        MapTile mapTile1 = new MapTile(oceanBiomes);
        assertEquals(MapTile.Type.OCEAN, mapTile1.tileType);

        // Test case where there's a lake biome
        ArrayList<String> lakeBiomes = new ArrayList<>();
        lakeBiomes.add("GROUND");
        lakeBiomes.add("LAKE");
        MapTile mapTile2 = new MapTile(lakeBiomes);
        assertEquals(MapTile.Type.LAKE, mapTile2.tileType);

        // Test case where there's neither ocean nor lake biome
        ArrayList<String> landBiomes = new ArrayList<>();
        landBiomes.add("GRASS");
        landBiomes.add("BEACH");
        landBiomes.add("FOREST");
        MapTile mapTile3 = new MapTile(landBiomes);
        assertEquals(MapTile.Type.LAND, mapTile3.tileType);

        // Test case where there's both ocean and lake biome
        ArrayList<String> mixedBiomes = new ArrayList<>();
        mixedBiomes.add("OCEAN");
        mixedBiomes.add("LAKE");
        MapTile mapTile4 = new MapTile(mixedBiomes);
        assertEquals(MapTile.Type.OCEAN, mapTile4.tileType);
    }
}