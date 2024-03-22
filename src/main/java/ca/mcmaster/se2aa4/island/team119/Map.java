package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Map {

    private final Logger logger = LogManager.getLogger();

    private HashMap<MapCoordinate, MapTile> map;
    private MapCoordinate droneCord;
    private MapTile inFront;
    private Integer distFront;
    private MapTile toRight;
    private Integer distRight;

    private MapTile toLeft;
    private Integer distLeft;

    ArrayList<POI> creeks;
    POI emergencySite;

    Map() {
        this.map = new HashMap<MapCoordinate, MapTile>();
        this.creeks = new ArrayList<POI>();
        this.inFront = new MapTile("UNKNOWN");
        this.toRight = new MapTile("UNKNOWN");
        this.toLeft = new MapTile("UNKNOWN");
        this.distFront = null;
        this.distRight = null;
        this.distLeft = null;
    }

    public void update(Response response, Direction droneHeading) throws IllegalArgumentException {
        LogManager.getLogger().info("IN UPDATE MAP");
        if(response instanceof EchoResponse) {
            update((EchoResponse) response);
        }
        else if(response instanceof ScanResponse) {
            LogManager.getLogger().info("UPDATING MAP USING SCAN RESPONSE");
            update((ScanResponse) response);
        }
        else if(response instanceof MovementResponse) {
            LogManager.getLogger().info("UPDATING MAP USING MOVEMENT RESPONSE");
            update((MovementResponse) response, droneHeading);
        }
        else { //Never should get to this else block
            throw new IllegalArgumentException("Not valid response type.");
        }
    }

    private void update(ScanResponse response) {
        MapTile tile = new MapTile(response.getBiomes());
        String site = response.getSite();
        ArrayList<String> creeks = response.getCreeks();

        LogManager.getLogger().info("UPDATING MAP DATA STRUCT");
        map.put(droneCord, tile);

        LogManager.getLogger().info("CHECKING TO UPDATE SITES");
        if(!site.isEmpty())
            this.emergencySite = new POI(tile, droneCord, site);


        LogManager.getLogger().info("CHECKING TO UPDATE CREEKS");
        if(!creeks.isEmpty()) {
            for (String creek : creeks)
                this.creeks.add(new POI(tile, droneCord, creek));
        }


        LogManager.getLogger().info("DONE UPDATE TO MAP VIA SCAN");
    }

    private void update(EchoResponse response) {
        Integer range = response.getRange();
        String found = response.getFound();

        if(response.getType() == ResultType.ECHOFWDRESULT) {
            inFront = new MapTile(found);
            distFront = range;
        }
        else if(response.getType() == ResultType.ECHOLEFTRESULT) {
            toLeft = new MapTile(found);
            distLeft = range;
        }
        else if(response.getType() == ResultType.ECHORIGHTRESULT) {
            toRight = new MapTile(found);
            distRight = range;
        }

    }

    private void update(MovementResponse response, Direction droneHeading) {
        LogManager.getLogger().info("IN MOVEMENT UPDATE FOR MAP");
        LogManager.getLogger().info("GETTING RESPONSE TYPE");
        ResultType resultType = response.getType();
        LogManager.getLogger().info("GOT IT, {}", resultType.toString());
        if(resultType == ResultType.FLYFWDRESULT) {
         //   droneCord.translate(1, droneHeading);
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distRight = null;
            this.distLeft = null;
        }
        else if(resultType == ResultType.FLYLEFTRESULT) {
//            droneCord.translate(1, droneHeading);
//            droneCord.translate(1, droneHeading.lookRight());
            this.inFront = new MapTile("UNKNOWN");
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distFront = null;
            this.distLeft = null;
            this.distRight = null;
        }
        else if(resultType == ResultType.FLYRIGHTRESULT) {
//            droneCord.translate(1, droneHeading);
//            droneCord.translate(1, droneHeading.lookLeft());
            this.inFront = new MapTile("UNKNOWN");
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distFront = null;
            this.distLeft = null;
            this.distRight = null;
        }
    }

    public MapTile inFront() { return this.inFront; }

    public MapTile toLeft() {
        return this.toLeft;
    }

    public MapTile toRight() {
        return this.toRight;
    }

    public Integer getDistFront() {
        return this.distFront != null ? this.distFront : -1;
    }

    public Integer getDistRight() {
        return this.distRight != null ? this.distRight : -1;
    }

    public Integer getDistLeft() {
        return this.distLeft != null ? this.distLeft : -1;
    }

}

