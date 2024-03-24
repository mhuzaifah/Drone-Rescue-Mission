package ca.mcmaster.se2aa4.island.team119;

public class POI {

    private final MapTile tile;
    private final MapCoordinate coordinate;
    private final String id;

    POI(MapTile tile, MapCoordinate cord, String id) {
        this.tile = tile;
        this.coordinate = cord;
        this.id = id;
    }

    // returns the tile as a MapTile
    public MapTile getTile() {
        return this.tile;
    }

    // returns the coordinate as a MapCoordinate
    public MapCoordinate getCoordinate() {
        return this.coordinate;
    }

    // returns the ID of the POI as a String
    public String getId() {
        return this.id;
    }


}
