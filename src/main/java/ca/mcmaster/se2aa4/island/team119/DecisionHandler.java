// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The DecisionMaker class overall encapsulates the decision-making process for the drone as it navigates the island,
// Starting off by determing which edge of the map it starts at, and which direction it should be facing then
// It then determines actions the drone should do based on its current state or if it should go back to base/return home

package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

import java.util.HashMap;

public class DecisionHandler {

    private Drone drone;
    private Map map;
    private BatteryManager batteryManager;
    private HashMap<SearchStateName, SearchState> states;
    private SearchState currState;
    private Operation decision;
    private Operation prevDecision;

    DecisionHandler(Drone drone, Map map) {
        this.drone = drone;
        this.map = map;
        map.setStartingEdge(determineStartingEdge(drone.getHeading()));
        this.batteryManager = new BatteryManager();
        this.prevDecision = null;
        this.states = new HashMap<>();
        this.states.put(SearchStateName.FINDISLAND, new FindIsland(this));
        this.states.put(SearchStateName.SEARCHISLAND, new IslandGridSearch(this));
        this.states.put(SearchStateName.RETURNHOME, new ReturnHome(this));
        this.currState = states.get(SearchStateName.FINDISLAND);
    }

    public JSONObject makeDecision() {

        setState();

        decision = currState.handle();
        prevDecision = decision;

        return executeDecision(decision);

    }

    private void setState() {
        switch (currState.getName()) {
            case FINDISLAND  -> {
                if(batteryManager.exceededThreshold(drone.getBattery(), map.getDronePosition())) {
                    this.currState = states.get(SearchStateName.SEARCHISLAND);
                }
                else if(currState.isFinished()) {
                    this.currState = new IslandGridSearch(this);
                }
            }
            case SEARCHISLAND -> {
                if(batteryManager.exceededThreshold(drone.getBattery(), map.getDronePosition())) {
                    this.currState = states.get(SearchStateName.RETURNHOME);
                }
                else if(currState.isFinished()) {
                    this.currState = states.get(SearchStateName.RETURNHOME);
                }
            }
            case RETURNHOME -> {}
            default -> throw new IllegalStateException("Unexpected value: " + currState.getName());
        }
    }

    private JSONObject executeDecision(Operation decision) {
        switch (this.decision.getAction()) {
            case SCAN -> {
                return drone.scan();
            }
            case ECHOFORWARD -> {
                return drone.echoFwd();
            }
            case ECHORIGHT -> {
                return drone.echoRight();
            }
            case ECHOLEFT -> {
                return drone.echoLeft();
            }
            case FLYFORWARD -> {
                return drone.flyFwd();
            }
            case FLYRIGHT -> {
                return drone.flyRight();
            }
            case FLYLEFT -> {
                return drone.flyLeft();
            }
            case STOP -> {
                return drone.stop();
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.decision.getAction());
        }
    }

    public Map getMap() {
        return this.map;
    }

    public Drone getDrone() {
        return this.drone;
    }

    public Operation getPrevOperation() {
        return this.prevDecision;
    }

    private Direction determineStartingEdge(Direction droneHeading) {
        Direction startingEdge = null;
        switch (droneHeading) {
            case NORTH -> {
                startingEdge = Direction.SOUTH;
            }
            case SOUTH -> {
                startingEdge = Direction.NORTH;
            }
            case EAST -> {
                startingEdge = Direction.WEST;
            }
            case WEST -> {
                startingEdge = Direction.EAST;
            }
        }
        return startingEdge;
    }

}
