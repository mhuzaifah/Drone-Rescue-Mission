package ca.mcmaster.se2aa4.island.team119;

public class BatteryManager {
    private final int baseBatteryThreshold = 15;
    private final int distanceMultiplier = 2;

    private final MapCoordinate startingPoint;
    //private MapCoordinate droneCord;

    public BatteryManager() {
        this.startingPoint = new MapCoordinate(0,0);
        //this.droneCord = new MapCoordinate(0,0);
    }

    /*int calculateDistance(MapCoordinate point1, MapCoordinate point2) {
        int deltaX = Math.abs(point1.getX() - point2.getX());
        int deltaY = Math.abs(point1.getY() - point2.getY());
        return (int) Math.ceil(Math.sqrt((deltaX * deltaX) + (deltaY * deltaY)));
    }*/

    public int calculateThreshold(MapCoordinate currentLocation) {
        int distanceFromBase = (int) Math.ceil(startingPoint.calculateDistance(startingPoint, currentLocation));
        return baseBatteryThreshold + (distanceFromBase * distanceMultiplier);
    }

    public Boolean exceededThreshold(Integer battery, MapCoordinate dronePosition) {
        Integer threshold = calculateThreshold(dronePosition);
        return battery < threshold;
    }
}
