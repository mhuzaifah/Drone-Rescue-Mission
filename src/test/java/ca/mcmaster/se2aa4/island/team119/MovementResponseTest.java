package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementResponseTest {

    @Test
    void testMovementResponseConstruction() {
        // Create dummy response information
        JSONObject responseInfo = new JSONObject();
        //some of the info for example is:
        responseInfo.put("cost", 10);
        responseInfo.put("status", "Success");
        JSONObject extras = new JSONObject();
        extras.put("detail", "Details about the movement response");
        responseInfo.put("extras", extras);

        // Create an example previous operation
        Operation prevOperation = new Operation(Action.SCAN);

        // Create a MovementResponse object
        MovementResponse movementResponse = new MovementResponse(responseInfo, prevOperation);

        // Assertions
        assertEquals(10, movementResponse.getCost());
        assertEquals("Success", movementResponse.getStatus());
        assertEquals(extras.toString(), movementResponse.getExtras().toString());
    }
}
