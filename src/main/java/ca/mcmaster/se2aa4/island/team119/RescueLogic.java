package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Objects;

import static ca.mcmaster.se2aa4.island.team119.Drone.State.*;

public class RescueLogic {

    private final Logger logger = LogManager.getLogger();
    ResultProcessor resultProcessor = new ResultProcessor();

    private Drone drone;

    RescueLogic(Drone drone) {
        this.drone = drone;
    }

    RadarSensor radarSensor = new RadarSensor(drone);

    boolean echoF, echoR, echoL = false;

    //String buffer = "";

    public void makeMove(JSONObject decision, String echoResult) {
        //Drone.State prevState = drone.getCurrentState();
        switch(drone.getCurrentState()) {
            /*case BUFFER -> {
                if (Objects.equals(buffer, "forward")) {
                    echoR = radarSensor.scanGround(decision, drone.direction.lookRight(), echoResult);
                } else if (Objects.equals(buffer, "right")) {
                    echoL = radarSensor.scanGround(decision, drone.direction.lookLeft(), echoResult);
                } else if (Objects.equals(buffer, "left")) {
                    echoF = radarSensor.scanGround(decision, drone.direction, echoResult);
                }

                /*if (prevState == ECHOFWD) {
                    echoR = radarSensor.scanGround(decision, drone.direction.lookRight(), echoResult);
                } else if (prevState == ECHOR) {
                    echoL = radarSensor.scanGround(decision, drone.direction.lookLeft(), echoResult);
                } else if (prevState == SCAN) {
                    echoF = radarSensor.scanGround(decision, drone.direction, echoResult);
                }
            }*/
            case BUFFERR, ECHOR -> {
                echoR = radarSensor.scanGround(decision, drone.direction.lookRight(), echoResult);
            }
            case BUFFERL, ECHOL -> {
                echoL = radarSensor.scanGround(decision, drone.direction.lookLeft(), echoResult);
            }
            case BUFFERF, ECHOFWD -> {
                echoF = radarSensor.scanGround(decision, drone.direction, echoResult);
            }
            /*case ECHOFWD -> {
                //decision.put("action", "echo");
                //decision.put("parameters", new JSONObject().put("direction", drone.direction.toString()));
                echoF = radarSensor.scanGround(decision, drone.direction, echoResult);
                //buffer = "forward";
            }*/
            case HEADINGR -> {
                decision.put("action", "heading");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookRight().toString()));
                drone.flyCount++;
                drone.direction = drone.direction.lookRight();
            }
            case HEADINGL -> {
                decision.put("action", "heading");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookLeft().toString()));
                drone.flyCount++;
                drone.direction = drone.direction.lookLeft();
            }
            case FLY -> {
                decision.put("action", "fly");
                drone.flyCount++;
            }
            /*case ECHOR -> {
                //decision.put("action", "echo");
                //decision.put("parameters", new JSONObject().put("direction", drone.direction.lookRight().toString()));
                echoR = radarSensor.scanGround(decision, drone.direction.lookRight(), echoResult);
                //buffer = "right";
            }*/
            /*case ECHOL -> {
                //decision.put("action", "echo");
                //decision.put("parameters", new JSONObject().put("direction", drone.direction.lookLeft().toString()));
                echoL = radarSensor.scanGround(decision, drone.direction.lookLeft(), echoResult);
            }*/
            case SCAN -> {
                //buffer = "scan";
                decision.put("action", "scan");
            }
            default -> { }
        }

        //transition(drone);
        transition();

    }

    //private void transition(Drone drone) {
    private void transition() {
        Drone.State prevState = drone.getCurrentState();
        switch(drone.getCurrentState()) {
            case FLY, HEADINGR, HEADINGL -> { drone.setCurrentState(Drone.State.SCAN); }
            case SCAN -> {//drone.setCurrentState(Drone.State.ECHOFWD);
                //buffer = "scan";
                drone.setCurrentState(Drone.State.BUFFERF);
            }
            case ECHOR -> {
                //buffer = "right";
                if (echoR) {
                    drone.setCurrentState(Drone.State.HEADINGR);
                } else {
                    //drone.setCurrentState(Drone.State.ECHOL);
                    drone.setCurrentState(Drone.State.BUFFERL);
                }
            }
            /*case BUFFER -> {
                if (Objects.equals(buffer, "forward")) {
                //if (prevState == ECHOFWD) {
                    drone.setCurrentState(Drone.State.ECHOR);
                } else if (Objects.equals(buffer, "right")) {
                //else if (prevState == ECHOR) {
                    drone.setCurrentState(Drone.State.ECHOL);
                } else if (Objects.equals(buffer, "scan")) {
                //else if (prevState == SCAN) {
                    drone.setCurrentState(ECHOFWD);
                }
            }*/
            case BUFFERR -> {
                drone.setCurrentState(Drone.State.ECHOR);
            }
            case BUFFERL -> {
                drone.setCurrentState(Drone.State.ECHOL);
            }
            case BUFFERF -> {
                drone.setCurrentState(ECHOFWD);
            }
            case ECHOL -> {
                if (echoL) {
                    drone.setCurrentState(Drone.State.HEADINGL);
                } else {
                    drone.setCurrentState(Drone.State.FLY);
                }
            }
            case ECHOFWD -> {
                //buffer = "forward";
                if (echoF) {
                    drone.setCurrentState(Drone.State.FLY);
                } else {
                    //drone.setCurrentState(Drone.State.ECHOR);
                    drone.setCurrentState(Drone.State.BUFFERR);
                }
            }
            default -> {}
        }
    }

}
