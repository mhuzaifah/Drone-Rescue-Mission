package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    MapCoordinate[] creeks;
    MapCoordinate[] emergencySites;

    Map() {
        this.map = new HashMap<MapCoordinate, MapTile>();
        this.inFront = new MapTile("UNKNOWN");
        this.toRight = new MapTile("UNKNOWN");
        this.toLeft = new MapTile("UNKNOWN");
        this.distFront = null;
        this.distRight = null;
        this.distLeft = null;
    }

    public void update(Response response, Direction droneHeading) {
        JSONObject extras = response.getExtras();
        logger.info("UPDATING MAP");
        switch(response.getType()) {
            case ECHOFWDRESULT -> {
                logger.info("JUST DID ECHO FWD");
                Integer range = extras.getInt("range");
                logger.info("GOT RANGE {}", range);
                String found = extras.getString("found");
                logger.info("GOT FOUND {}", found);
                logger.info("MAKING MAP TILE IN FRONT");
                inFront = new MapTile(found);
                distFront = range;
                logger.info("MAP TILE IS {}", inFront);
//                MapCoordinate cord = new MapCoordinate(droneCord.x, droneCord.y);
//                cord.translate(range, droneHeading);
//                map.put(cord, inFront);
            }
            case ECHOLEFTRESULT -> {
                Integer range = extras.getInt("range");
                String found = extras.getString("found");
                toLeft = new MapTile(found);
                distLeft = range;
//                MapCoordinate cord = new MapCoordinate(droneCord.x, droneCord.y);
//                cord.translate(range, droneHeading);
//                map.put(cord, toLeft);
            }
            case ECHORIGHTRESULT -> {
                Integer range = extras.getInt("range");
                String found = extras.getString("found");
                toRight = new MapTile(found);
                distRight = range;
              //  MapCoordinate cord = new MapCoordinate(droneCord.x, droneCord.y);
              //  cord.translate(range, droneHeading);
              //  map.put(cord, toRight);
            }
            case FLYFWDRESULT -> {
              //  droneCord.translate(1, droneHeading);
                this.toRight = new MapTile("UNKNOWN");
                this.toLeft = new MapTile("UNKNOWN");
                this.distRight = null;
                this.distLeft = null;
            }
            case FLYRIGHTRESULT -> {
//                droneCord.translate(1, droneHeading);
//                droneCord.translate(1, droneHeading.lookLeft());
                this.inFront = new MapTile("UNKNOWN");
                this.toRight = new MapTile("UNKNOWN");
                this.toLeft = new MapTile("UNKNOWN");
                this.distFront = null;
                this.distLeft = null;
                this.distRight = null;
            }
            case FLYLEFTRESULT -> {
//                droneCord.translate(1, droneHeading);
//                droneCord.translate(1, droneHeading.lookRight());
                this.inFront = new MapTile("UNKNOWN");
                this.toRight = new MapTile("UNKNOWN");
                this.toLeft = new MapTile("UNKNOWN");
                this.distFront = null;
                this.distLeft = null;
                this.distRight = null;
            }
            case SCANRESULT -> {
                map.put(droneCord, new MapTile("OCEAN")); //Temporary, don't currently need to test scanning
            }
        }
    }

    public MapTile inFront() {
        logger.info("GETTING IN FRONT");
        return this.inFront;
    }

    public MapTile toLeft() {
        return this.toLeft;
    }

    public MapTile toRight() {
        return this.toRight;
    }

    public Integer getDistFront() {
        return this.distFront;
    }

    public Integer getDistRight() {
        return this.distRight;
    }

    public Integer getDistLeft() {
        return this.distLeft;
    }

//    public boolean isExplored(int[] pos){
//        for (Position prevPo : prevPos) {
//            if ((prevPo.getPos(0) == pos[0]) & (prevPo.getPos(1) == pos[1])) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void setCurrentPos(Position newCurrentPos) {
//        prevPos.add(currentPos);
//        currentPos = newCurrentPos;
//    }
//
//    public void findCreek() {}

//    public void turnRight() {
//        Position pos;
//        switch(drone.direction){
//            case EAST ->{
//                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)+1);
//            }
//            case WEST -> {
//                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)-1);
//            }
//            case NORTH -> {
//                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)-1);
//            }
//            case SOUTH -> {
//                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)+1);
//            }
//            default -> {
//                pos = null;
//            }
//        }
//        this.setCurrentPos(pos);
//    }
//
//    public void turnLeft() {
//        Position pos;
//        switch(drone.direction){
//            case EAST ->{
//                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)-1);
//            }
//            case WEST -> {
//                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)+1);
//            }
//            case NORTH -> {
//                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)-1);
//            }
//            case SOUTH -> {
//                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)+1);
//            }
//            default -> {
//                pos = null;
//            }
//        }
//        this.setCurrentPos(pos);
//    }
//
//    public void forward() {
//        Position pos;
//        switch(drone.direction){
//            case EAST ->{
//                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1));
//            }
//            case WEST -> {
//                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1));
//            }
//            case NORTH -> {
//                pos = new Position(currentPos.getPos(0), currentPos.getPos(1)-1);
//            }
//            case SOUTH -> {
//                pos = new Position(currentPos.getPos(0), currentPos.getPos(1)+1);
//            }
//            default -> {
//                pos = null;
//            }
//        }
//        this.setCurrentPos(pos);
//    }
}

