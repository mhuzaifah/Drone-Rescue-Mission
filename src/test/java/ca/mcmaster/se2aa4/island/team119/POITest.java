package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class POITest {

    POI poi;

    @BeforeEach
    public void setUp() {
        poi = new POI(new MapTile("GROUND"), new MapCoordinate(0, 0), "d0076222-5a8f-485d-a9ae-c793b616201d");
    }

    @Test
    public void testGetTile() {
        assertTrue((new MapTile("GROUND")).sameTileType(poi.getTile()));
    }

    @Test
    public void testGetCoordinate() {
        assertEquals((new MapCoordinate(0, 0)).toString(), poi.getCoordinate().toString());
    }

    @Test
    public void testGetID() {
        assertEquals("d0076222-5a8f-485d-a9ae-c793b616201d", poi.getId());
    }
}
