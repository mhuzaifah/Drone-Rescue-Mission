package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DroneTest {

    private Drone drone;

    @BeforeEach
    public void setUp() {
        this.drone = new Drone("E", 7000);
    }

    @Test
    public void testScan() {
        JSONObject action = drone.scan();
        assertEquals("scan", action.getString("action"));
    }

    @Test
    public void testEchoFwd() {
        JSONObject action = drone.echoFwd();
        assertEquals("echo", action.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().toString())).toString(), action.getJSONObject("parameters").toString());
    }

    @Test
    public void testEchoRight() {
        JSONObject action = drone.echoRight();
        assertEquals("echo", action.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().lookRight().toString())).toString(), action.getJSONObject("parameters").toString());
    }

    @Test
    public void testEchoLeft() {
        JSONObject action = drone.echoLeft();
        assertEquals("echo", action.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().lookLeft().toString())).toString(), action.getJSONObject("parameters").toString());
    }

    @Test
    public void testFlyFwd() {
        JSONObject action = drone.flyFwd();
        assertEquals("fly", action.getString("action"));
    }

    @Test
    public void testFlyRight() {
        JSONObject action = drone.flyRight();
        assertEquals("heading", action.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().toString())).toString(), action.getJSONObject("parameters").toString());
    }

    @Test
    public void testFlyLeft() {
        JSONObject action = drone.flyLeft();
        assertEquals("heading", action.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().toString())).toString(), action.getJSONObject("parameters").toString());
    }

    @Test
    public void testStop() {
        JSONObject action = drone.stop();
        assertEquals("stop", action.getString("action"));
    }

    @Test
    public void testUpdate() {
        JSONObject responseInfo = new JSONObject();
        responseInfo.put("cost", 7);
        responseInfo.put("status", "OK");

        JSONObject extras = new JSONObject();
        extras.put("found", "OUT_OF_RANGE");
        extras.put("range", 32);
        responseInfo.put("extras", extras);

        EchoResponse response = new EchoResponse(responseInfo, new Operation(Action.ECHOFORWARD));

        int updatedBattery = drone.getBattery() - response.getCost();

        drone.update(response);

        assertEquals(updatedBattery, drone.getBattery());
    }

    @Test
    public void testGetHeading() {
        assertEquals(Direction.EAST, drone.getHeading());
    }

    @Test
    public void testGetBattery() {
        assertEquals(7000, drone.getBattery());
    }
}