package ca.mcmaster.se2aa4.island.team119;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionCoordinatorTest {

    private Drone drone;
    private Map map;
    private JSONObject initInfo;
    private MissionCoordinator missionCoordinator;
    private JSONObject decision;

    @BeforeEach
    void setUp() {
        initInfo = new JSONObject();
        initInfo.put("heading", "N");
        initInfo.put("budget", 7000);
        missionCoordinator = new MissionCoordinator(initInfo);
        drone = missionCoordinator.getDrone();
        map = missionCoordinator.getMap();
        decision = missionCoordinator.makeDecision();
    }

    @Test
    void testMakeDecisionFindIslandState() {
        assertEquals("echo", decision.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().lookRight().toString())).toString(), decision.getJSONObject("parameters").toString());
    }
    @Test
    void testMakeDecisionSearchIslandState() {
        missionCoordinator.currState = new IslandGridSearch(missionCoordinator);
        JSONObject decision2 = missionCoordinator.makeDecision();
        assertEquals("echo", decision2.getString("action"));
        assertEquals((new JSONObject().put("direction", drone.getHeading().toString())).toString(), decision2.getJSONObject("parameters").toString());
    }

    @Test
    void testMakeDecisionReturnHomeState() {
        missionCoordinator.currState = new ReturnHome(missionCoordinator);
        JSONObject decision2 = missionCoordinator.makeDecision();
        assertEquals("stop", decision2.getString("action"));
    }
}