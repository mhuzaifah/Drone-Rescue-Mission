package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class DecisionMaker {

    private Drone drone;
    private SearchState currState;
    private Operation decision;
    private Operation prevDecision;
    private Map map;

    DecisionMaker(Drone drone, Map map) {
        this.currState = new FindIsland(this);
        this.drone = drone;
        this.map = map;
        this.prevDecision = null;
    }

    public JSONObject makeDecision() {

        decision = currState.handle();
        prevDecision = decision;

        LogManager.getLogger().info("CHECKING CURRENT MAIN STATE");
        LogManager.getLogger().info("{}", currState.getName());
        switch (currState.getName()) {
            case FINDISLAND  -> {
                if(currState.isFinished()) {
                    this.currState = new IslandGridSearch(this);
                }
            }
            case SEARCHISLAND -> {
                if(currState.isFinished()) {
                    this.currState = new ReturnHome();
                }
            }
            default -> {}
        }
        LogManager.getLogger().info("AFTER CHECK -> {}", currState.getName());

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

}
