package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class RescueLogic {

    private final Logger logger = LogManager.getLogger();
    ResultProcessor resultProcessor = new ResultProcessor();

    private Drone drone;

    RescueLogic(Drone drone) {
        this.drone = drone;
    }

    public void makeMove(JSONObject decision) {

        switch(drone.getCurrentState()) {
            case ECHOFWD -> {
                decision.put("action", "echo");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.toString()));
            }
            case FLY -> {
                decision.put("action", "fly");
                drone.flyCount++;
            }
            case ECHOR -> {
                decision.put("action", "echo");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookRight().toString()));
            }
            case ECHOL -> {
                decision.put("action", "echo");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookLeft().toString()));
            }
            case SCAN -> {
                decision.put("action", "scan");
            }
            default -> { }
        }

        transition();

    }

    private void transition() {
        switch(drone.getCurrentState()) {
            case ECHOFWD, FLY -> { drone.setCurrentState(Drone.State.SCAN); }
            case SCAN -> { drone.setCurrentState(Drone.State.ECHOR); }
            case ECHOR -> { drone.setCurrentState(Drone.State.ECHOL); }
            case ECHOL -> { drone.setCurrentState(Drone.State.FLY); }
            default -> {}
        }
    }

}
