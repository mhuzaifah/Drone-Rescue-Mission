// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Map
// holds all info about map and updates the map

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

    // updates the map using the response from the last action executed
    // uses the type of the last response to update accordingly
    // parameter - the response returned, direction of the drone
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

    // updates the map, including creeks and sites, after a scan
    // parameters - response from scanning, of type ScanResponse
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

    // updates map, including the range, using response from echo
    // parameters - response from echo, of type EchoResponse
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

    // updates the map, including the drone coordinate, using the movement response
    // parameters - response from movement, of type MovementResponse
    private void update(MovementResponse response, Direction droneHeading) {
        ResultType resultType = response.getType();
        if(resultType == ResultType.FLYFWDRESULT) {
            droneCord.translateFwd(droneHeading);
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distRight = null;
            this.distLeft = null;
        }
        else if(resultType == ResultType.FLYLEFTRESULT) {
            droneCord.translateLeft(droneHeading);
            this.inFront = new MapTile("UNKNOWN");
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distFront = null;
            this.distLeft = null;
            this.distRight = null;
        }
        else if(resultType == ResultType.FLYRIGHTRESULT) {
            droneCord.translateRight(droneHeading);
            this.inFront = new MapTile("UNKNOWN");
            this.toRight = new MapTile("UNKNOWN");
            this.toLeft = new MapTile("UNKNOWN");
            this.distFront = null;
            this.distLeft = null;
            this.distRight = null;
        }
    }

    // these methods are getters, they return the tiles and distances to the front, right and left, of type MapTile and Integer, respectively
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

    // this method calculates the closest creek to the emergency site
    // returns the closest creek as a POI
    // throws IndexOutOfBoundsException if no creeks have been found
    public POI findClosestCreek() throws IndexOutOfBoundsException{
        POI closestCreek = creeks.get(creeks.size()-1);
        if (emergencySite != null) {
            double minDistance = Double.MAX_VALUE;

            for (POI creek : creeks) {
                double distance = droneCord.calculateDistance(emergencySite.getCoordinate(), creek.getCoordinate());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCreek = creek;
                }
            }
        }
        return closestCreek;
    }

    // sets starting edge of type Direction
    public void setStartingEdge(Direction startingEdge) {
        if(this.startingEdge == null)
            this.startingEdge = startingEdge;
    }

    // getter - returns starting edge
    public Direction getStartingEdge() {
        return this.startingEdge;
    }

}