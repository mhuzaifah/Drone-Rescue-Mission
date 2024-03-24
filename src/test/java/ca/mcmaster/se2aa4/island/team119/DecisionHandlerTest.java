package ca.mcmaster.se2aa4.island.team119;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecisionHandlerTest {

    private Drone drone;
    private Map map;
    private DecisionHandler decisionHandler;

    @BeforeEach
    void setUp() {
        drone = new Drone("NORTH", 7000); // Initialize drone with heading NORTH and battery level 7000
        map = new Map(); // Initialize the Map object
        decisionHandler = new DecisionHandler(drone, map); // Initialize the DecisionHandler object
    }
    @Test
    void testMakeDecision() {
        // Set the current state of the DecisionHandler object to a custom state
        decisionHandler.currState = new CustomState();

        // Perform decision making
        JSONObject decisionResult = decisionHandler.makeDecision();

        // Assertions
        assertNotNull(decisionResult);
        assertEquals(DecisionHandler.SearchStateName.FINDISLAND, decisionHandler.getCurrState().getName());
        // Add more assertions based on expected actions and responses
    }

    @Test
    void testMakeDecision_SearchingIsland() {
        decisionHandler.getCurrState().setFinished(true);

        // Perform decision making
        JSONObject decisionResult = decisionHandler.makeDecision();

        // Assertions
        assertNotNull(decisionResult);
        assertEquals(DecisionHandler.SearchStateName.SEARCHISLAND, decisionHandler.getCurrState().getName());
        // Add more assertions based on expected actions and responses
    }

    @Test
    void testMakeDecision_ReturningHome() {
        decisionHandler.getCurrState().setFinished(true);

        // Perform decision making
        JSONObject decisionResult = decisionHandler.makeDecision();

        // Assertions
        assertNotNull(decisionResult);
        assertEquals(DecisionHandler.SearchStateName.RETURNHOME, decisionHandler.getCurrState().getName());
        // Add more assertions based on expected actions and responses
    }

    // Add more test cases for other methods and edge cases as needed

}
