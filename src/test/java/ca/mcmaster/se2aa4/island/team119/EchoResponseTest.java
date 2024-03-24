package ca.mcmaster.se2aa4.island.team119;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EchoResponseTest {

    @Test
    void testEchoResponseConstruction() {
        // Create dummy response information
        JSONObject responseInfo = new JSONObject();
        responseInfo.put("cost", 5);
        responseInfo.put("status", "Success");
        JSONObject extras = new JSONObject();
        extras.put("range", 10);
        extras.put("found", "Creek");
        responseInfo.put("extras", extras);

        // Create an example previous operation
        Operation prevOperation = new Operation(Action.ECHOFORWARD);

        // Create an EchoResponse object
        EchoResponse echoResponse = new EchoResponse(responseInfo, prevOperation);

        // Assertions
        assertEquals(5, echoResponse.getCost());
        assertEquals("Success", echoResponse.getStatus());
        assertNotEquals("Failure", echoResponse.getStatus());
        assertEquals(10, echoResponse.getRange());
        assertNotEquals(9, echoResponse.getRange());
        assertEquals("Creek", echoResponse.getFound());
        assertNotEquals("Ocean", echoResponse.getFound());
        assertEquals(ResultType.ECHOFWDRESULT, echoResponse.getType());
    }
}
