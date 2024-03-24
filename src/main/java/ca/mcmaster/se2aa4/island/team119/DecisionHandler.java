// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The DecisionMaker class overall encapsulates the decision-making process for the drone as it navigates the island,
// Starting off by determing which edge of the map it starts at, and which direction it should be facing then
// It then determines actions the drone should do based on its current state or if it should go back to base/return home

package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

import java.util.HashMap;

public class DecisionHandler {

    private Drone drone; // The drone object responsible for executing actions
    private Map map; // The map object representing the map's features
    private BatteryManager batteryManager; // Manages the drone's battery threshold
    private HashMap<SearchStateName, SearchState> states; // Stores different search states
    SearchState currState; // The current state of the drone's grid search process/pattern
    private Operation decision; // The decision made by the drone for the current state
    private Operation prevDecision; // The previous decision made by the drone

    // Constructs a DecisionHandler object with the drone and map.
    // Initializes the drone's starting edge of the map and sets the initial search state to finding the island.
        // drone -- The drone object responsible for executing actions.
        // map -- The map object representing map features and updating responses.
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

    // Makes a decision based on the current state of the drone's search process (each state has a decision related to it)
    // Updates the current state and returns the decision to be executed.
    // returns the decision to be executed by the drone as a JSONObject.
    public JSONObject makeDecision() {

        setState();

        decision = currState.handle();
        prevDecision = decision;

        return executeDecision();

    }

    // A private helper method that sets the current state of the drone's search process based on its current state and battery level.
    // Sets state to whether it should keep finding the island, or move on to searching the island, or move onto going back to base,
    // due to low battery or completed search
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

    // Executes the given decision by calling the respective drone action.
    // returns the response received from executing the decision as a JSONObject
    private JSONObject executeDecision() {
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

    // a getter method that retrieves the map object and returns it.
    public Map getMap() {
        return this.map;
    }

    // a getter method that retrieves the drone object responsible for executing actions and returns the drone object.
    public Drone getDrone() {
        return this.drone;
    }

    // a getter method that retrieves the previous decision made by the drone and returns the previous decision.
    public Operation getPrevOperation() {
        return this.prevDecision;
    }

    // Determines the edge of the map where the drone starts based on the drone's initial heading.
        // droneHeading -- The initial direction the drone is facing.
    // returns the starting edge of the drone on the map.
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
