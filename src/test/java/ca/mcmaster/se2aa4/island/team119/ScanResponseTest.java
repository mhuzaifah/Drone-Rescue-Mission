package ca.mcmaster.se2aa4.island.team119;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScanResponseTest {

    private JSONObject ResponseInfo() {
        // Create dummy response information
        JSONObject responseInfo = new JSONObject();
        responseInfo.put("cost", 5);
        responseInfo.put("status", "Success");
        JSONObject extras = new JSONObject();
        extras.put("biomes", ResponseInfoBiomes());
        extras.put("creeks", ResponseInfoCreeks());
        extras.put("sites", ResponseInfoSites());
        responseInfo.put("extras", extras);
        return responseInfo;
    }

    private JSONArray ResponseInfoBiomes() {
        // Create a JSONArray of biomes
        JSONArray biomesArray = new JSONArray();
        biomesArray.put("Ocean");
        biomesArray.put("Beach");
        return biomesArray;
    }

    private JSONArray ResponseInfoCreeks() {
        // Create a JSONArray of creeks
        JSONArray creeksArray = new JSONArray();
        creeksArray.put("56e4F");
        creeksArray.put("423b95");
        return creeksArray;
    }

    private JSONArray ResponseInfoSites() {
        // Create a JSONArray of sites
        JSONArray sitesArray = new JSONArray();
        sitesArray.put("678f3a");
        return sitesArray;
    }

    @Test
    void testScanResponseConstruction() {
        // Create dummy response information
        JSONObject responseInfo = ResponseInfo();

        // Create an example previous operation
        Operation prevOperation = new Operation(Action.SCAN);

        // Create a ScanResponse object
        ScanResponse scanResponse = new ScanResponse(responseInfo, prevOperation);

        // Assertions
        assertEquals(5, scanResponse.getCost());
        assertEquals("Success", scanResponse.getStatus());
        assertNotEquals(Arrays.asList("Forest", "Mountain"), scanResponse.getBiomes());
        assertEquals(Arrays.asList("Ocean", "Beach"), scanResponse.getBiomes());
        assertEquals(Arrays.asList("56e4F", "423b95"), scanResponse.getCreeks());
        assertNotEquals(Arrays.asList("56e5E", "145b95"), scanResponse.getCreeks());
        assertNotEquals("Cave", scanResponse.getSite());
        assertEquals("678f3a", scanResponse.getSite());
        assertEquals(ResultType.SCANRESULT, scanResponse.getType());
    }

    @Test
    void testParseBiomes() {
        // Create an example previous operation
        Operation prevOperation = new Operation(Action.SCAN);

        // Create a ScanResponse object
        ScanResponse scanResponse = new ScanResponse(ResponseInfo(), prevOperation);

        // Parse biomes
        ArrayList<String> parsedBiomes = scanResponse.parseBiomes(ResponseInfoBiomes());

        // Assertions
        assertEquals(Arrays.asList("Ocean", "Beach"), parsedBiomes);
    }

    @Test
    void testParseCreeks() {
        // Create an example previous operation
        Operation prevOperation = new Operation(Action.SCAN);

        // Create a ScanResponse object
        ScanResponse scanResponse = new ScanResponse(ResponseInfo(), prevOperation);

        // Parse creeks
        ArrayList<String> parsedCreeks = scanResponse.parseCreeks(ResponseInfoCreeks());

        // Assertions
        assertNotEquals(Arrays.asList("56e5E", "145b95"), parsedCreeks);
        assertEquals(Arrays.asList("56e4F", "423b95"), parsedCreeks);
    }

    @Test
    void testParseSites() {
        // Create an example previous operation
        Operation prevOperation = new Operation(Action.SCAN);

        // Create a ScanResponse object
        ScanResponse scanResponse = new ScanResponse(ResponseInfo(), prevOperation);

        // Parse sites
        String parsedSite = scanResponse.parseSites(ResponseInfoSites());

        // Assertions
        assertEquals("678f3a", parsedSite);
    }
}
