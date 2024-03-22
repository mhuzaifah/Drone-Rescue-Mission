package ca.mcmaster.se2aa4.island.team119;

public class POI {

    final MapTile tile;
    final MapCoordinate coordinate;
    final String id;

    POI(MapTile tile, MapCoordinate cord, String id) {
        this.tile = tile;
        this.coordinate = cord;
        this.id = id;
    }


}
