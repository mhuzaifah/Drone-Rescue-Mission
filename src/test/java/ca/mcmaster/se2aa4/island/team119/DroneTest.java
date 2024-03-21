package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DroneTest {

    private Drone drone;

    @BeforeEach
    public void setUp() {
        // Initialize a Drone object with some initial direction and battery level for testing
        this.drone = new Drone("E", 7000); // Example: Facing North with 100% battery
    }

    @Test
    public void testScan() {
        JSONObject result = drone.scan();
        assertEquals("scan", result.getString("action"));
        // You may add more assertions to check the structure or content of the JSON object returned by scan()
    }

    // Add more test methods to cover other functionalities of the Drone class
}