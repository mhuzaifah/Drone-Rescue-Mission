package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class DroneController {

    private final Drone drone;
    private JSONObject decision;
    private String echoResult;

    DroneController(Drone drone, JSONObject decision, String echoResult) {
        this.drone = drone;
        this.decision = decision;
        this.echoResult = echoResult;
    }

    RadarSensor radarSensor = new RadarSensor();

    public void forward() {
        decision.put("action", "fly");
        drone.flyCount++;
    }
    public void turnLeft() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", drone.direction.lookLeft().toString()));
        drone.flyCount++;
        drone.direction = drone.direction.lookLeft();
    }
    public void turnRight() {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", drone.direction.lookRight().toString()));
        drone.flyCount++;
        drone.direction = drone.direction.lookRight();
    }
    public void returnToBase() {
        decision.put("action", "stop");
    }
    public void scan() {
        decision.put("action", "scan");
    }
    public boolean echoRight() {
        return radarSensor.echoGround(decision, drone.direction.lookRight(), echoResult);
    }
    public boolean echoLeft() {
        return radarSensor.echoGround(decision, drone.direction.lookLeft(), echoResult);
    }
    public boolean echoForward() {
        return radarSensor.echoGround(decision, drone.direction, echoResult);
    }

}
