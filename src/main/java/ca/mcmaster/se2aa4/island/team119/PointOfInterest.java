package ca.mcmaster.se2aa4.island.team119;

public class PointOfInterest implements MapTile {

    enum PoiType {
        CREEK,
        EMERGENCYSITE
    }

    Integer id;
    PoiType type;

    PointOfInterest(Integer id, PoiType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public void displayInfo() {}

}
