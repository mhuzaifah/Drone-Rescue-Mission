package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.BeforeEach;

public class DecisionMakerTest {

    private DecisionHandler decisionMaker;
    private Drone drone;
    private Map map;

    @BeforeEach
    public void setUp() {
        drone = new Drone("E", 7000);
        map = new Map(); // You may need to set up the map accordingly
        decisionMaker = new DecisionHandler(drone, map);
    }


}
