package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatteryThresholdTest {

    // Test cases for different current locations
    MapCoordinate[] currentLocations = {
            new MapCoordinate(0, 0),  // Same as starting point
            new MapCoordinate(1, 0),  // close case to starting
            new MapCoordinate(0, 1),  // inverted close case to starting
            new MapCoordinate(-3, -4),// testing to see if it works with negatives
            new MapCoordinate(3, 4),  // testing to see if we get the same value as the negatives
            new MapCoordinate(-3, 4), // testing one negative, one positive
            new MapCoordinate(3, -4)  // testing one negative, one positive inverted
    };

    @Test
    void testCalculateDistance() {
        // Create an instance of BatteryThreshold
        MapCoordinate startingPoint = new MapCoordinate(0, 0);
        MapCoordinate anotherPoint = new MapCoordinate(-20, 100);
        BatteryThreshold batteryThreshold = new BatteryThreshold(startingPoint);

        // Expected threshold values for each test case
        int[] expectedDistances00 = {
                0,                    // (0,0) -> (0,0)
                1,                    // (0,0) -> (1,0)
                1,                    // (0,0) -> (0,1)
                5,                    // (0,0) -> (-3,-4)
                5,                    // (0,0) -> (3,4)
                5,                    // (0,0) -> (-3,4)
                5                     // (0,0) -> (3,-4)
        };

        int[] expectedDistances20100 = {
                102,                    // (-20,100) -> (0,0)
                103,                    // (-20,100) -> (1,0)
                101,                    // (-20,100) -> (0,1)
                106,                    // (-20,100) -> (-3,-4)
                99,                    // (-20,100) -> (3,4)
                98,                    // (-20,100) -> (-3,4)
                105                     // (-20,100) -> (3,-4)
        };

        // Perform the test for each data point
        for (int i = 0; i < currentLocations.length; i++) {
            int calculatedDistance = batteryThreshold.calculateDistance(startingPoint, currentLocations[i]);
            assertEquals(expectedDistances00[i], calculatedDistance);
        }

        for (int i = 0; i < currentLocations.length; i++) {
            int calculatedDistance = batteryThreshold.calculateDistance(anotherPoint, currentLocations[i]);
            assertEquals(expectedDistances20100[i], calculatedDistance);
        }
    }


    @Test
    void testCalculateThreshold() {
        // Define the base battery threshold and distance multiplier
        int baseBatteryThreshold = 15;
        int distanceMultiplier = 2;

        // Define the starting point coordinates
        MapCoordinate startingPoint = new MapCoordinate(0, 0);

        // Create a BatteryThreshold object
        BatteryThreshold batteryThreshold = new BatteryThreshold(startingPoint);

        // Expected threshold values for each test case
        int[] expectedThresholds = {
                baseBatteryThreshold,
                baseBatteryThreshold + distanceMultiplier,
                baseBatteryThreshold + distanceMultiplier,
                baseBatteryThreshold + (5 * distanceMultiplier),
                baseBatteryThreshold + (5 * distanceMultiplier),
                baseBatteryThreshold + (5 * distanceMultiplier),
                baseBatteryThreshold + (5 * distanceMultiplier)
        };

        // Perform the tests
        for (int i = 0; i < currentLocations.length; i++) {
            int actualThreshold = batteryThreshold.calculateThreshold(currentLocations[i]);
            assertEquals(expectedThresholds[i], actualThreshold);
        }
    }
}
