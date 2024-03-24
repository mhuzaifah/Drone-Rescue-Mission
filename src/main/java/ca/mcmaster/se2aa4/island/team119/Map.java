package ca.mcmaster.se2aa4.island.team119;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

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
    private Direction startingEdge = null;

    Map() {
        this.map = new HashMap<MapCoordinate, MapTile>();
        this.creeks = new ArrayList<POI>();
        this.emergencySite = null;
        this.inFront = new MapTile("UNKNOWN");
        this.toRight = new MapTile("UNKNOWN");
        this.toLeft = new MapTile("UNKNOWN");
        this.distFront = null;
        this.distRight = null;
        this.distLeft = null;
        this.droneCord = new MapCoordinate(0,0);
    }

    public void update(Response response, Direction droneHeading) throws IllegalArgumentException {
        if(response instanceof EchoResponse) {
            update((EchoResponse) response);
        }
        else if(response instanceof ScanResponse) {
            update((ScanResponse) response);
        }
        else if(response instanceof MovementResponse) {
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

        map.put(droneCord, tile);

        if(!site.isEmpty()) {
            this.emergencySite = new POI(tile, new MapCoordinate(droneCord.getX(), droneCord.getY()), site);
        }

        if(!creeks.isEmpty()) {
            for (String creek : creeks){
                this.creeks.add(new POI(tile, new MapCoordinate(droneCord.getX(), droneCord.getY()), creek));
            }
        }
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
        ResultType resultType = response.getType();
        if(resultType == ResultType.FLYFWDRESULT) {
            droneCord.flyFwd(droneHeading);
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distRight = null;
            this.distLeft = null;
        }
        else if(resultType == ResultType.FLYLEFTRESULT) {
            droneCord.flyLeft(droneHeading);
            this.inFront = new MapTile("UNKNOWN");
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distFront = null;
            this.distLeft = null;
            this.distRight = null;
        }
        else if(resultType == ResultType.FLYRIGHTRESULT) {
            droneCord.flyRight(droneHeading);
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

    public POI findClosestCreek() throws IndexOutOfBoundsException{
        POI closestCreek = creeks.get(creeks.size()-1);
        if (emergencySite != null) {
            int emergencySiteX = emergencySite.getCoordinate().getX();
            int emergencySiteY = emergencySite.getCoordinate().getY();

            int creekX;
            int creekY;

            double minDistance = Double.MAX_VALUE;

            for (POI creek : creeks) {
                creekX = creek.getCoordinate().getX();
                creekY = creek.getCoordinate().getY();
                double distance = Math.sqrt(Math.pow((emergencySiteX - creekX), 2) + Math.pow((emergencySiteY - creekY), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCreek = creek;
                }
            }
        }
        return closestCreek;
    }

    public void setStartingEdge(Direction startingEdge) {
        if(this.startingEdge == null)
            this.startingEdge = startingEdge;
    }

    public Direction getStartingEdge() {
        return this.startingEdge;
    }

}