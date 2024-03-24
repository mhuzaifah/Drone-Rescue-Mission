package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;

public class MapTest {

    Map map;
    JSONObject responseInfo;

    @BeforeEach
    public void setUp() {
        map = new Map();
        responseInfo = new JSONObject();
        responseInfo.put("cost", 7);
        responseInfo.put("status", "OK");
    }

    @Test
    public void testUpdateEcho() {
        JSONObject extras = new JSONObject();
        extras.put("found", "OUT_OF_RANGE");
        extras.put("range", 32);
        responseInfo.put("extras", extras);

        EchoResponse response = new EchoResponse(responseInfo, new Operation(Action.ECHOFORWARD));

        map.update(response, Direction.NORTH);

        assertEquals(map.inFront().tileType, (new MapTile(response.getFound())).tileType);
        assertEquals(map.getDistFront(), response.getRange());
    }

    @Test
    public void testUpdateScanNotFound() {
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray());

        JSONArray biomes = new JSONArray();
        biomes.put("BEACH");
        extras.put("biomes", biomes);

        extras.put("sites", new JSONArray());

        responseInfo.put("extras", extras);

        ScanResponse response = new ScanResponse(responseInfo, new Operation(Action.SCAN));

        map.update(response, Direction.NORTH);

        assertNull(map.emergencySite);
        assertEquals(new ArrayList<POI>(), map.creeks);
    }

    @Test
    public void testUpdateScanCreekFound() {
        JSONObject extras = new JSONObject();

        JSONArray creeks = new JSONArray();
        creeks.put("d0076222-5a8f-485d-a9ae-c793b616201d");
        extras.put("creeks", creeks);

        JSONArray biomes = new JSONArray();
        biomes.put("BEACH");
        extras.put("biomes", biomes);

        extras.put("sites", new JSONArray());

        responseInfo.put("extras", extras);

        ScanResponse response = new ScanResponse(responseInfo, new Operation(Action.SCAN));

        map.update(response, Direction.NORTH);

        POI creek  = new POI(new MapTile(response.getBiomes()), new MapCoordinate(0, 0), response.getCreeks().get(0));

        assertEquals(creek.getId(), map.creeks.get(0).getId());
        assertEquals(creek.getCoordinate().toString(), map.creeks.get(0).getCoordinate().toString());
    }

    @Test
    public void testUpdateScanEmergencySiteFound() {
        JSONObject extras = new JSONObject();

        extras.put("creeks", new JSONArray());

        JSONArray biomes = new JSONArray();
        biomes.put("BEACH");
        extras.put("biomes", biomes);

        JSONArray sites = new JSONArray();
        sites.put("fcfea781-7c6d-4517-8757-7708f0f18877");
        extras.put("sites", sites);

        responseInfo.put("extras", extras);

        ScanResponse response = new ScanResponse(responseInfo, new Operation(Action.SCAN));

        map.update(response, Direction.NORTH);

        POI site  = new POI(new MapTile(response.getBiomes()), new MapCoordinate(0, 0), response.getSite());

        assertEquals(site.getId(), map.emergencySite.getId());
        assertEquals(site.getCoordinate().toString(), map.emergencySite.getCoordinate().toString());
    }

    @Test
    public void testUpdateMovementFLYFWD() {
        responseInfo.put("extras", new JSONObject());
        MovementResponse response = new MovementResponse(responseInfo, new Operation(Action.FLYFORWARD));

        map.update(response, Direction.NORTH);

        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toRight()));
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toLeft()));
        assertEquals(-1, map.getDistRight());
        assertEquals(-1, map.getDistLeft());
    }

    @Test
    public void testUpdateMovementFLYLEFT() {
        responseInfo.put("extras", new JSONObject());
        MovementResponse response = new MovementResponse(responseInfo, new Operation(Action.FLYLEFT));

        map.update(response, Direction.NORTH);

        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toRight()));
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toLeft()));
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.inFront()));
        assertEquals(-1, map.getDistRight());
        assertEquals(-1, map.getDistLeft());
        assertEquals(-1, map.getDistFront());
    }

    @Test
    public void testUpdateMovementFLYRIGHT() {
        responseInfo.put("extras", new JSONObject());
        MovementResponse response = new MovementResponse(responseInfo, new Operation(Action.FLYRIGHT));

        map.update(response, Direction.NORTH);

        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toRight()));
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toLeft()));
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.inFront()));
        assertEquals(-1, map.getDistRight());
        assertEquals(-1, map.getDistLeft());
        assertEquals(-1, map.getDistFront());
    }

    @Test
    public void testInFront() {
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.inFront()));
    }

    @Test
    public void testToLeft() {
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toLeft()));
    }

    @Test
    public void testToRight() {
        assertTrue(new MapTile("UNKNOWN").sameTileType(map.toRight()));
    }

    @Test
    public void testGetDistFront() {
        assertEquals(-1, map.getDistFront());
    }

    @Test
    public void testGetDistRight() {
        assertEquals(-1, map.getDistRight());
    }

    @Test
    public void testGetDistLeft() {
        assertEquals(-1, map.getDistLeft());
    }

    @Test
    public void testFindClosestCreekNotFound() {
        Executable exception = () -> map.findClosestCreek();
        assertThrows(IndexOutOfBoundsException.class, exception);
    }

    @Test
    public void testFindClosestCreekSiteFound() {
        // no creek site, creek site
        Executable exception = () -> map.findClosestCreek();
        assertThrows(IndexOutOfBoundsException.class, exception);
    }

    @Test
    public void testFindClosestCreekFound() {
        map.creeks.add(new POI(new MapTile("GROUND"), new MapCoordinate(0, 0), "be398c6e-9471-4949-991d-9be788d8616e"));
        map.creeks.add(new POI(new MapTile("GROUND"), new MapCoordinate(1, 1), "9d1058ae-e12c-40ec-9507-80490fd76875"));
        assertEquals("9d1058ae-e12c-40ec-9507-80490fd76875", map.findClosestCreek().getId());
    }

    @Test
    public void testFindClosestCreekBothFound() {
        map.creeks.add(new POI(new MapTile("GROUND"), new MapCoordinate(1, 1), "be398c6e-9471-4949-991d-9be788d8616e"));
        map.creeks.add(new POI(new MapTile("GROUND"), new MapCoordinate(0, 0), "9d1058ae-e12c-40ec-9507-80490fd76875"));
        map.emergencySite = new POI(new MapTile("GROUND"), new MapCoordinate(3, 4), "fea0e031-77f4-41e4-a44a-af57d9fdc1b4");
        assertEquals("be398c6e-9471-4949-991d-9be788d8616e", map.findClosestCreek().getId());
    }
}
