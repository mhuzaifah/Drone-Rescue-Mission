package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class Drone {

    Direction direction;
    Integer batteryLevel;
    DroneController droneController = new DroneController();
    RescueLogic rescueLogic = new RescueLogic(this);
    int flyCount; //Used to keep track of how far the drone has flown. Will have to refactor into using coordinates instead that are connected with the Map
    private State currentState;
    public enum State {
        FLY,
        ECHOFWD,
        ECHOR,
        ECHOL,
        SCAN,
        HEADINGR,
        HEADINGL,
        BUFFERR,
        BUFFERL,
        BUFFERF,
        STOP
    }

    Drone(JSONObject info) {
        this.direction = determineInitDirection(info.getString("heading"));
        this.batteryLevel = info.getInt("budget");
        this.currentState = State.ECHOFWD;
        this.flyCount = 0;
    }

    private Direction determineInitDirection(String directionAbv) {
        switch (directionAbv) {
            case "N" -> { return Direction.NORTH; }
            case "S" -> { return Direction.SOUTH; }
            case "E" -> { return Direction.EAST; }
            case "W" -> { return Direction.WEST; }
            default -> throw new IllegalStateException("Unexpected value: " + directionAbv);
        }
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(State newState) {
        this.currentState = newState;
    }

    public void explore(JSONObject decision, String echoResult) {
        rescueLogic.makeMove(decision, echoResult);
    }
}

