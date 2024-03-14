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
    boolean firstTurnR, firstTurnL = false;
    int counter = 0;

    boolean alternating = false;


    //String buffer = "";

    public void makeMove(JSONObject decision, String echoResult) {
        //Drone.State prevState = drone.getCurrentState();
        switch(drone.getCurrentState()) {
            case BUFFERR -> {
                echoR = radarSensor.echoGround(decision, drone.direction.lookRight(), echoResult);
            }
            case ECHOR -> {
                echoR = radarSensor.echoGround(decision, drone.direction.lookRight(), echoResult);
            }
            case BUFFERL -> {
                echoL = radarSensor.echoGround(decision, drone.direction.lookLeft(), echoResult);
            }
            case ECHOL -> {
                echoL = radarSensor.echoGround(decision, drone.direction.lookLeft(), echoResult);
            }
            case BUFFERF -> {
                echoF = radarSensor.echoGround(decision, drone.direction, echoResult);
            }
            case ECHOFWD -> {
                echoF = radarSensor.echoGround(decision, drone.direction, echoResult);
            }
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
            case SCAN -> {
                //buffer = "scan";
                decision.put("action", "scan");
            }
            default -> { }
        }
        transition();
    }

    private void transition() {
        //Drone.State prevState = drone.getCurrentState();
        switch(drone.getCurrentState()) {
            case ECHOFWD -> {
                drone.setCurrentState(Drone.State.BUFFERF);
            }
            case BUFFERF -> {
                if (echoF) {
                    drone.setCurrentState(Drone.State.FLY);
                } else {
                    if (counter == 0) {
                        drone.setCurrentState(Drone.State.ECHOL);
                    } else {
                        //drone.setCurrentState(Drone.State.ALTERNATING);
                        if (alternating) {
                            alternating = false;
                            //drone.setCurrentState(Drone.State.ECHOR);
                            firstTurnR = true;
                            drone.setCurrentState(Drone.State.HEADINGR);
                        } else {
                            alternating = true;
                            firstTurnL = true;
                            //drone.setCurrentState(Drone.State.ECHOL);
                            drone.setCurrentState(Drone.State.HEADINGL);
                        }
                    }
                }
            }
            case ECHOL -> {
                drone.setCurrentState(Drone.State.BUFFERL);
            }
            case BUFFERL -> {
                if (echoL) {
                    drone.setCurrentState(Drone.State.HEADINGL);
                } else {
                    drone.setCurrentState(Drone.State.ECHOR);
                }
            }
            case ECHOR -> {
                drone.setCurrentState(Drone.State.BUFFERR);
            }
            case BUFFERR -> {
                if (echoR) {
                    drone.setCurrentState(Drone.State.HEADINGR);
                } else {
                    drone.setCurrentState(Drone.State.FLY);
                }
            }
            case FLY -> {drone.setCurrentState(Drone.State.SCAN);}
            case HEADINGR, HEADINGL -> {
                counter++;
                drone.setCurrentState(Drone.State.SCAN);
            }
            case SCAN -> {
                if (firstTurnR) {
                    firstTurnR = false;
                    drone.setCurrentState(Drone.State.HEADINGR);
                } else if (firstTurnL) {
                    firstTurnL = false;
                    drone.setCurrentState(Drone.State.HEADINGL);
                } else {
                    drone.setCurrentState(Drone.State.ECHOFWD);
                }
            }
            default -> {}
        }
    }

}

//maybe make another switch where the states are if on ground what to do and if on land what transitions to do
