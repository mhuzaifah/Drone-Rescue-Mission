package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import static ca.mcmaster.se2aa4.island.team119.Drone.State.*;

public class RescueLogic {

    private final Logger logger = LogManager.getLogger();
    ResultProcessor resultProcessor = new ResultProcessor();

    private Drone drone;

    RescueLogic(Drone drone) {
        this.drone = drone;
    }

    RadarSensor radarSensor = new RadarSensor();
    //Map map = new Map(drone);

    boolean echoF, echoR, echoL = false;
    boolean firstTurnR, firstTurnL = false;
    int counter = 0;

    boolean alternating = false;


    public void makeMove(JSONObject decision, String echoResult) {
        switch(drone.getCurrentState()) {
            case BUFFERR, ECHOR -> {
                echoR = radarSensor.echoGround(decision, drone.direction.lookRight(), echoResult);
            }
            case BUFFERL, ECHOL -> {
                echoL = radarSensor.echoGround(decision, drone.direction.lookLeft(), echoResult);
            }
            case BUFFERF, ECHOFWD -> {
                echoF = radarSensor.echoGround(decision, drone.direction, echoResult);
            }
            case HEADINGR -> {
                decision.put("action", "heading");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookRight().toString()));
                drone.flyCount++;
                drone.direction = drone.direction.lookRight();
                //map.turnRight();
            }
            case HEADINGL -> {
                decision.put("action", "heading");
                decision.put("parameters", new JSONObject().put("direction", drone.direction.lookLeft().toString()));
                drone.flyCount++;
                drone.direction = drone.direction.lookLeft();
                //map.turnLeft();
            }
            case FLY -> {
                decision.put("action", "fly");
                drone.flyCount++;
                //map.forward();
            }
            case SCAN -> {
                decision.put("action", "scan");
            }
            case STOP -> {
                decision.put("action", "stop");
            }
            default -> { }
        }
        transition();
    }

    private void transition() {
        /*if (!Objects.equals(creek, "")){
            drone.setCurrentState(STOP);
        }*/
        if (drone.batteryLevel < 50){
            drone.setCurrentState(STOP);
        }

        switch(drone.getCurrentState()) {
            case ECHOFWD -> {
                drone.setCurrentState(BUFFERF);
            }
            case BUFFERF -> {
                if (echoF) {
                    drone.setCurrentState(FLY);
                } else {
                    if (counter == 0) {
                        drone.setCurrentState(ECHOL);
                    } else {
                        if (alternating) {
                            alternating = false;
                            firstTurnR = true;
                            drone.setCurrentState(HEADINGR);
                        } else {
                            alternating = true;
                            firstTurnL = true;
                            drone.setCurrentState(HEADINGL);
                        }
                    }
                }
            }
            case ECHOL -> {
                drone.setCurrentState(BUFFERL);
            }
            case BUFFERL -> {
                if (echoL) {
                    firstTurnL = false;
                    drone.setCurrentState(HEADINGL);
                } else if (firstTurnL){
                    drone.setCurrentState(STOP);
                } else {
                    drone.setCurrentState(ECHOR);
                }
            }
            case ECHOR -> {
                drone.setCurrentState(BUFFERR);
            }
            case BUFFERR -> {
                if (echoR) {
                    firstTurnR = false;
                    drone.setCurrentState(HEADINGR);
                } else if (firstTurnR){
                    drone.setCurrentState(STOP);
                } else{
                    drone.setCurrentState(FLY);
                }
            }
            case FLY -> {drone.setCurrentState(SCAN);}
            case HEADINGR, HEADINGL -> {
                counter++;
                drone.setCurrentState(SCAN);
            }
            case SCAN -> {
                if (firstTurnR) {
                    drone.setCurrentState(ECHOR);
                } else if (firstTurnL) {
                    drone.setCurrentState(ECHOL);
                } else {
                    drone.setCurrentState(ECHOFWD);
                }
            }
            default -> {}
        }
    }

}