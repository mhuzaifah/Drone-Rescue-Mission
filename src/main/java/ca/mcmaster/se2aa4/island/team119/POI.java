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

    public MapTile getTile() {
        return this.tile;
    }

    public MapCoordinate getCoordinate() {
        return this.coordinate;
    }

    public String getId() {
        return this.id;
    }


}
