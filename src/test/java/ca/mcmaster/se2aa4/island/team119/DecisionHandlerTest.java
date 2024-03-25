package ca.mcmaster.se2aa4.island.team119;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.*;

class DecisionHandlerTest {

    private Drone drone;
    private Map map;
    private DecisionHandler decisionHandler;
    private JSONObject decision;

    @BeforeEach
    void setUp() {
        drone = new Drone("N", 7000);
        map = new Map();
        decisionHandler = new DecisionHandler(drone, map);
        decision = decisionHandler.makeDecision();
    }

    @Test
    void testMakeDecisionFindIslandState() {
        assertEquals("echo", decision.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().lookRight().toString())).toString(), decision.getJSONObject("parameters").toString());
    }
    @Test
    void testMakeDecisionSearchIslandState() {
        decisionHandler.currState = new IslandGridSearch(decisionHandler);
        JSONObject decision2 = decisionHandler.makeDecision();
        assertEquals("echo", decision2.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().toString())).toString(), decision2.getJSONObject("parameters").toString());
    }

    @Test
    void testMakeDecisionReturnHomeState() {
        decisionHandler.currState = new ReturnHome(decisionHandler);
        JSONObject decision2 = decisionHandler.makeDecision();
        assertEquals("stop", decision2.getString("action"));
    }
}