package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfoTranslatorTest {

    private JSONObject responseInfo;

    @BeforeEach
    public void setUp() {
        this.responseInfo = new JSONObject();

        responseInfo.put("cost", 7);
        responseInfo.put("status", "OK");
        JSONObject extras = new JSONObject();
        JSONArray creeks = new JSONArray();
        creeks.put("d0076222-5a8f-485d-a9ae-c793b616201d");
        extras.put("creeks", creeks);
        JSONArray biomes = new JSONArray();
        biomes.put("BEACH");
        extras.put("biomes", biomes);
        JSONArray sites = new JSONArray();
        sites.put("fcfea781-7c6d-4517-8757-7708f0f18877");
        extras.put("found", "OUT_OF_RANGE");
        extras.put("range", 32);
        extras.put("sites", sites);
        responseInfo.put("extras", extras);
    }

    @Test
    public void testCreateResponseScan() {
        InfoTranslator infoTranslator = new InfoTranslator();

        Operation scan = new Operation(Action.SCAN);
        ScanResponse scanResponse = new ScanResponse(responseInfo, scan);

        Response infoTranslatorResponse = infoTranslator.createResponse(responseInfo, scan);

        assertEquals(scanResponse.getBiomes().get(0), infoTranslatorResponse.getExtras().getJSONArray("biomes").getString(0));
        assertEquals(scanResponse.getCreeks().get(0), infoTranslatorResponse.getExtras().getJSONArray("creeks").getString(0));
        assertEquals(scanResponse.getSite(), infoTranslatorResponse.getExtras().getJSONArray("sites").getString(0));
    }

    @Test
    public void testCreateResponseEcho() {
        InfoTranslator infoTranslator = new InfoTranslator();

        Operation echoFWD = new Operation(Action.ECHOFORWARD);
        EchoResponse echoResponse = new EchoResponse(responseInfo, echoFWD);

        Response infoTranslatorResponse = infoTranslator.createResponse(responseInfo, echoFWD);

        assertEquals(echoResponse.getFound(), infoTranslatorResponse.getExtras().getString("found"));
        assertEquals(echoResponse.getRange(), infoTranslatorResponse.getExtras().getInt("range"));
    }

    @Test
    public void testCreateResponseMovement() {
        InfoTranslator infoTranslator = new InfoTranslator();

        Operation fly = new Operation(Action.FLYFORWARD);
        MovementResponse movementResponse = new MovementResponse(responseInfo, fly);

        Response infoTranslatorResponse = infoTranslator.createResponse(responseInfo, fly);

        assertEquals(movementResponse.getCost(), infoTranslatorResponse.getCost());
        assertEquals(movementResponse.getType(), infoTranslatorResponse.getType());
    }
}
