package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    void testGetType() {
        // Mock a responseInfo JSONObject
        JSONObject mockResponseInfo = new JSONObject();
        mockResponseInfo.put("cost", 10);
        mockResponseInfo.put("status", "OK");
        mockResponseInfo.put("extras", new JSONObject());

        // Create a mock Operation object
        Operation mockOperation1 = new Operation(Action.SCAN);
        // Create a concrete Response subclass instance for testing
        Response response1 = new Response(mockResponseInfo, mockOperation1) {
        };

        // Create another mock Operation object
        Operation mockOperation2 = new Operation(Action.ECHORIGHT);
        // Create a concrete Response subclass instance for testing
        Response response2 = new Response(mockResponseInfo, mockOperation2) {
        };

        // Create a mock Operation object
        Operation mockOperation3 = new Operation(Action.ECHOFORWARD);
        // Create a concrete Response subclass instance for testing
        Response response3 = new Response(mockResponseInfo, mockOperation3) {
        };

        // Create a mock Operation object
        Operation mockOperation4 = new Operation(Action.FLYLEFT);
        // Create a concrete Response subclass instance for testing
        Response response4 = new Response(mockResponseInfo, mockOperation4) {
        };

        // Create a mock Operation object
        Operation mockOperation5 = new Operation(Action.STOP);
        // Create a concrete Response subclass instance for testing
        Response response5 = new Response(mockResponseInfo, mockOperation5) {
        };

        // Verify that the getType method returns the expected ResultType
        assertEquals(ResultType.SCANRESULT, response1.getType());
        assertEquals(ResultType.ECHORIGHTRESULT, response2.getType());
        assertEquals(ResultType.ECHOFWDRESULT, response3.getType());
        assertEquals(ResultType.FLYLEFTRESULT, response4.getType());
        assertEquals(ResultType.STOPRESULT, response5.getType());
    }

    @Test
    void testGetCost() {
        // Mock a responseInfo JSONObject
        JSONObject mockResponseInfo = new JSONObject();
        mockResponseInfo.put("cost", 20);
        mockResponseInfo.put("status", "OK");
        mockResponseInfo.put("extras", new JSONObject());

        // Create a mock Operation object
        Operation mockOperation = new Operation(Action.ECHOLEFT);

        // Create a concrete Response subclass instance for testing
        Response response = new Response(mockResponseInfo, mockOperation) {
        };

        // Verify that the getCost method returns the expected cost
        assertEquals(20, response.getCost());
        assertNotEquals(21, response.getCost());
        assertNotEquals(10, response.getCost());
        assertNotEquals(0, response.getCost());
    }

    @Test
    void testGetExtras() {
        // Mock a responseInfo JSONObject with extras
        JSONObject extras = new JSONObject();
        extras.put("key1", "value1");
        extras.put("key2", "value2");
        JSONObject mockResponseInfo = new JSONObject();
        mockResponseInfo.put("cost", 10);
        mockResponseInfo.put("status", "OK");
        mockResponseInfo.put("extras", extras);

        // Create a mock Operation object
        Operation mockOperation = new Operation(Action.FLYFORWARD);

        // Create a concrete Response subclass instance for testing
        Response response = new Response(mockResponseInfo, mockOperation) {
        };

        // Verify that the getExtras method returns the expected JSONObject
        assertEquals(extras.toString(), response.getExtras().toString());
    }

    @Test
    void testGetStatus() {
        // Mock a responseInfo JSONObject
        JSONObject mockResponseInfo = new JSONObject();
        mockResponseInfo.put("cost", 20);
        String status = "Success";
        mockResponseInfo.put("status", status);
        mockResponseInfo.put("extras", new JSONObject());

        // Create a mock Operation object
        Operation mockOperation = new Operation(Action.ECHOLEFT);

        // Create a concrete Response subclass instance for testing
        Response response = new Response(mockResponseInfo, mockOperation) {
        };

        assertEquals(status, response.getStatus());
    }
}
