// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// BatteryThreshold class calculates what the threshold should be for the battery edge case dynamically
// The farther the drone is from the base, the greater the battery threshold
// The drone keeps checking the battery and compares it with this threshold
// If the battery is ever lower than this threshold, the drone will return back to base

package ca.mcmaster.se2aa4.island.team119;
public class BatteryManager {
    private final int baseBatteryThreshold = 15; // starting threshold
    private final int distanceMultiplier = 2; // multiplying factor for distance compared to threshold
    private final MapCoordinate startingPoint; // the coordinates of the base

    // This method is constructing the BatteryThreshold object
    // It initializes the startingPoint of the drone to be (0,0)
    public BatteryManager() {
        this.startingPoint = new MapCoordinate(0,0);
    }

    // The calculateThreshold method is responsible for taking the distance between the starting point of the drone and its current location
    // and determining what the battery threshold should be based on that distance
    // It takes one parameter as a type from the MapCoordinate class
         // currentLocation -- this parameter give the instructor the current coordinates of the drone from which it calculates the threshold (since the current location keeps changing, it is a parameter)
    // The method returns the battery threshold as an integer
    public int calculateThreshold(MapCoordinate currentLocation) {
        int distanceFromBase = (int) Math.ceil(startingPoint.calculateDistance(startingPoint, currentLocation));
        return baseBatteryThreshold + (distanceFromBase * distanceMultiplier);
    }

    // The exceededThreshold method checks to see if the battery is less than the threshold
    // Returns a true or false based on if it is less than or not
        // the parameters it takes are:
        // battery -- what the battery of the drone is currently
        // currentLocation -- this parameter give the instructor the current coordinates of the drone which is used as the input to calculate the threshold
    public Boolean exceededThreshold(Integer battery, MapCoordinate currentLocation) {
        Integer threshold = calculateThreshold(currentLocation);
        return battery < threshold;
    }
}
