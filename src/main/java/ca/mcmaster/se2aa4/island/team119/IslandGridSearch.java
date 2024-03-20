package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;

public class IslandGridSearch implements SearchState, SearchAlgo {

    private DecisionMaker decisionMaker;
    private final State stateName = State.SEARCHISLAND;
    private Boolean finished = false;
    private SubState currSubState;
    enum SubState {
        EXPLORE,
        TURN,
        ADJUST
    }
    private Queue<Operation> operations;
    private TurnDirection turnDirection;
    private Boolean loopedBack = false;
    enum TurnDirection {
        RIGHT,
        LEFT
    }

    IslandGridSearch(DecisionMaker decisionMaker) {
        this.decisionMaker = decisionMaker;
        this.currSubState = SubState.EXPLORE;
        this.operations = new ArrayDeque<>();
        setTurnDirection();
        operations.add(new Operation(Action.ECHOFORWARD));
    }

    private void setTurnDirection() {
        Direction droneHeading = decisionMaker.getDrone().getHeading();

        //NEED TO CHANGE BASED OFF STARTING CORDS
        //VARIES DEPENDING ON WHICH CYCLE YOURE ON
        if(droneHeading == Direction.SOUTH || droneHeading == Direction.EAST)
            turnDirection = loopedBack ? TurnDirection.LEFT : TurnDirection.RIGHT;
        else
            turnDirection = loopedBack ? TurnDirection.RIGHT : TurnDirection.LEFT;
    }

    @Override
    public Operation handle() {

        LogManager.getLogger().info("CURRENT SUBSTATE {}", currSubState.toString());
        switch (currSubState) {
            case EXPLORE -> {
                transition();
                return operations.remove();
            }
            case TURN -> {
                transition();
                return operations.remove();
            }
            case ADJUST -> {
                transition();
                return operations.remove();
            }
            default -> {
                return null;
            }
        }

    }

    @Override
    public Operation search() {
        return null;
    }

    @Override
    public void transition() {

        switch (currSubState) {
            case EXPLORE -> {
                if(decisionMaker.getPrevOperation().isEchoFwd()) {
                    if (decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                        LogManager.getLogger().info("OCEAN IN FRONT SO TURNING");
                        currSubState = SubState.TURN;

                        //Setting up turning logic
                        if(turnDirection == TurnDirection.LEFT) {
                            operations.add(new Operation(Action.FLYLEFT));
                            operations.add(new Operation(Action.FLYLEFT));
                        }
                        else {
                            operations.add(new Operation(Action.FLYRIGHT));
                            operations.add(new Operation(Action.FLYRIGHT));
                        }
                        operations.add(new Operation(Action.SCAN));

                    }
                    else {
                        LogManager.getLogger().info("NO OCEAN IN FRONT, INSTEAD {}", decisionMaker.getMap().inFront().toString());
                        Integer distInFront = decisionMaker.getMap().getDistFront();
                        LogManager.getLogger().info("DIST INFRONT IS {}", distInFront);
                        if (distInFront == 0) {
                            LogManager.getLogger().info("FLYING, SCANNING");
                            operations.add(new Operation(Action.FLYFORWARD));
                            operations.add(new Operation(Action.SCAN));
                        } else {
                            LogManager.getLogger().info("JUST FLYING TO NEXT LAND");
                            for(int i=0; i<distInFront; i++)
                                operations.add(new Operation(Action.FLYFORWARD));
                        }
                        LogManager.getLogger().info("ECHOING");
                        operations.add(new Operation(Action.ECHOFORWARD));
                    }
                }
            }
            case TURN -> {
                if(decisionMaker.getPrevOperation().isScan()) {
                    setTurnDirection();
                    operations.add(new Operation(Action.ECHOFORWARD));
                }
                else if(decisionMaker.getPrevOperation().isEchoFwd()) {

                    if(decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                        if(!loopedBack) {
                            currSubState = SubState.ADJUST;
                            operations.add(new Operation(Action.ECHORIGHT));
                            operations.add(new Operation(Action.ECHOLEFT));
                        }
                        else
                            operations.add(new Operation(Action.STOP));
                    }
                    else {
                        currSubState = SubState.EXPLORE;
                        operations.add(new Operation(Action.ECHOFORWARD));
                    }

                }
            }
            case ADJUST -> {
                if(decisionMaker.getPrevOperation().isEchoLeft()) {
                    Integer dist;
                    Operation turn;
                    if(decisionMaker.getMap().toRight().sameTileType(new MapTile("GROUND"))) {
                        dist = decisionMaker.getMap().getDistRight();
                        turn = new Operation(Action.FLYRIGHT);
                        LogManager.getLogger().info("DISTANCE RIGHT {}", dist);
                    }
                    else {
                        dist = decisionMaker.getMap().getDistLeft();
                        turn = new Operation(Action.FLYLEFT);
                        LogManager.getLogger().info("DISTANCE LEFT {}", dist);
                    }

                    //Getting drone in correct position
                    operations.add(turn);
                    if(dist == 0) {
                        operations.add(new Operation(Action.FLYFORWARD));
                    }
                    else if(dist == 1){
                        operations.add(new Operation(Action.FLYFORWARD));
//                        operations.add(new Operation(Action.FLYFORWARD));
//                        operations.add(new Operation(Action.FLYFORWARD));
                    }
                    operations.add(turn);
                    operations.add(turn);
                    operations.add(turn);

                    operations.add(new Operation(Action.SCAN));
                }
                else if(decisionMaker.getPrevOperation().isScan()) {
                    LogManager.getLogger().info("SETTING BACK TO EXPLORE AFTER ADJUSTING");
                    currSubState = SubState.EXPLORE;
                    loopedBack = true;
                    setTurnDirection();
                    LogManager.getLogger().info("LOOPED BACK IS {}", loopedBack);
                    operations.add(new Operation(Action.ECHOFORWARD));
                    LogManager.getLogger().info("SIZE {}", operations.size());
                }
            }
        }
    }

    @Override
    public Boolean isFinished() {
        return this.finished;
    }

    @Override
    public State getName() {
        return this.stateName;
    }

}