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
    private Operation turn;
    private Operation echoForTurn;
    private Boolean loopedBack = false;

    IslandGridSearch(DecisionMaker decisionMaker) {
        this.decisionMaker = decisionMaker;
        this.currSubState = SubState.EXPLORE;
        this.operations = new ArrayDeque<>();
        setDirectionOfInterest();
        operations.add(new Operation(Action.ECHOFORWARD));
    }

    private void setDirectionOfInterest() {
        Direction droneHeading = decisionMaker.getDrone().getHeading();

        //NEED TO CHANGE BASED OFF STARTING CORDS
        //VARIES DEPENDING ON WHICH CYCLE YOU'RE ON
        if(droneHeading == Direction.SOUTH || droneHeading == Direction.EAST) {
            turn = new Operation(loopedBack ? Action.FLYLEFT : Action.FLYRIGHT);
            echoForTurn = new Operation(loopedBack ? Action.ECHOLEFT : Action.ECHORIGHT);
        }
        else {
            turn = new Operation(loopedBack ? Action.FLYRIGHT : Action.FLYLEFT);
            echoForTurn = new Operation(loopedBack ? Action.ECHORIGHT : Action.ECHOLEFT);
        }
    }

    @Override
    public Operation handle() {

        LogManager.getLogger().info("CURRENT SUBSTATE {}", currSubState.toString());
        switch (currSubState) {
            case EXPLORE, TURN, ADJUST -> {
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
        setDirectionOfInterest();
        switch (currSubState) {
            case EXPLORE -> {
                if(decisionMaker.getPrevOperation().isEchoFwd()) {
                    if (decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                        LogManager.getLogger().info("OCEAN IN FRONT SO TURNING");
                        operations.add(new Operation(Action.FLYFORWARD));
                        operations.add(new Operation(Action.SCAN));
                        currSubState = SubState.TURN;
                        setDirectionOfInterest();
                        operations.add(echoForTurn);
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
                            for(int i=0; i < distInFront; i++)
                                operations.add(new Operation(Action.FLYFORWARD));
                        }
                        LogManager.getLogger().info("ECHOING");
                        operations.add(new Operation(Action.ECHOFORWARD));
                    }
                }
            }
            case TURN -> {
                Operation prevOperation = decisionMaker.getPrevOperation();
                if(prevOperation.isEchoRight() || prevOperation.isEchoLeft()) {
                    Integer distance = prevOperation.isEchoRight() ? decisionMaker.getMap().getDistRight() : decisionMaker.getMap().getDistLeft();
                    if (distance > 1) {
                        operations.add(turn);
                        operations.add(turn);
                        operations.add(new Operation(Action.ECHOFORWARD));
                    }
                    else {
                        operations.add(new Operation(Action.FLYFORWARD));
                        operations.add(echoForTurn);
                    }
                }
                else if(prevOperation.isEchoFwd()) {
                    LogManager.getLogger().info("CHECKING ECHO FORWARD RESULT TO SEE IF WE SHOULD ADJUST OR NOT");
                    if(decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                        LogManager.getLogger().info("LOOPED BACK {}", loopedBack);
                        if(!loopedBack) {
                            LogManager.getLogger().info("GOING TO ADJUST", loopedBack);
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
                    else {
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
                    setDirectionOfInterest();
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