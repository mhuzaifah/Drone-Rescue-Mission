package ca.mcmaster.se2aa4.island.team119;

import java.util.ArrayDeque;
import java.util.Queue;

public class FindIsland implements SearchState {

    private DecisionMaker decisionMaker;
    private SubState currSubState;
    private final State stateName = State.FINDISLAND;
    private Boolean finished = false;
    private Boolean islandInitInFront;
    private Boolean updatedExtremaOperations = false;
    private Action echoForExtremas;
    private Action turnForExtremas;
    private Queue<Operation> operations;

    FindIsland(DecisionMaker dm) {
        this.decisionMaker = dm;
        this.currSubState = SubState.CHECKSURROUNDINGS;
    }

    enum SubState {
        CHECKSURROUNDINGS,
        FINDEXTREMAONE,
        FINDEXTREMATWO,
        NAVIGATETOISLAND,
    }

    @Override
    public Operation handle() {
        switch (currSubState) {
            case CHECKSURROUNDINGS -> {
                return checkingSurroundings();
            }
            case FINDEXTREMAONE -> {
                return findingExtremaOne();
            }
            case FINDEXTREMATWO -> {
                return findingExtremaTwo();
            }
            case NAVIGATETOISLAND -> {
                return navigatingToIsland();
            }
            default -> throw new IllegalStateException("Unexpected value: " + currSubState);
        }
    }

    @Override
    public void transition() {
        switch (currSubState) {
            case CHECKSURROUNDINGS -> {
                islandInitInFront = decisionMaker.getMap().inFront().sameTileType(new MapTile("GROUND"));
                currSubState = SubState.FINDEXTREMAONE;
            }
            case FINDEXTREMAONE-> {
                if (!decisionMaker.getMap().toLeft().sameTileType(new MapTile("GROUND")) && decisionMaker.getMap().toRight().sameTileType(new MapTile("OCEAN")))
                    currSubState = SubState.FINDEXTREMATWO;
                else if (!decisionMaker.getMap().toRight().sameTileType(new MapTile("GROUND")) && decisionMaker.getMap().toLeft().sameTileType(new MapTile("OCEAN")))
                    currSubState = SubState.FINDEXTREMATWO;
            }
            case FINDEXTREMATWO -> {
                if (decisionMaker.getMap().toLeft().sameTileType(new MapTile("GROUND")) || decisionMaker.getMap().toRight().sameTileType(new MapTile("GROUND"))) {
                    currSubState = SubState.NAVIGATETOISLAND;
                    operations = new ArrayDeque<>();
                    operations.add(new Operation(Action.ECHOFORWARD));
                }
            }
            case NAVIGATETOISLAND -> {
                if(operations.isEmpty())
                    this.finished = true;
            }
            default -> {}
        }
    }

    private Operation checkingSurroundings() {
        if(decisionMaker.getPrevOperation() == null) {
            return new Operation(Action.ECHORIGHT);
        }
        else if (decisionMaker.getPrevOperation().isEchoRight()) {
            return new Operation(Action.ECHOLEFT);
        }
        else if (decisionMaker.getPrevOperation().isEchoLeft()) {
            return new Operation(Action.ECHOFORWARD);
        }
        else {
            transition();
            Integer distanceLeft = decisionMaker.getMap().getDistLeft();
            Integer distanceRight = decisionMaker.getMap().getDistRight();
            if (islandInitInFront) {
                if (distanceLeft <= distanceRight) {
                    echoForExtremas = Action.ECHORIGHT;
                    turnForExtremas = Action.FLYRIGHT;
                    return new Operation(Action.FLYLEFT);
                } else {
                    echoForExtremas = Action.ECHOLEFT;
                    turnForExtremas = Action.FLYLEFT;
                    return new Operation(Action.FLYRIGHT);
                }
            }
            else {
                if (distanceLeft <= distanceRight) {
                    echoForExtremas = Action.ECHOLEFT;
                    turnForExtremas = Action.FLYLEFT;
                    return new Operation(Action.FLYRIGHT);
                } else {
                    echoForExtremas = Action.ECHORIGHT;
                    turnForExtremas = Action.FLYRIGHT;
                    return new Operation(Action.FLYLEFT);
                }
            }
        }
    }

    private Operation findingExtremaOne() {
        if(decisionMaker.getPrevOperation().isEcho()) {
            transition();
            if(currSubState == SubState.FINDEXTREMAONE)
                return new Operation(Action.FLYFORWARD);
            else
                return new Operation(turnForExtremas);
        }
        else {
            return new Operation(echoForExtremas);
        }
    }

    private Operation findingExtremaTwo() {
        if(!islandInitInFront && !updatedExtremaOperations) {
            echoForExtremas = echoForExtremas.oppositeAction();
            turnForExtremas = turnForExtremas.oppositeAction();
            updatedExtremaOperations = true;
        }

        if(decisionMaker.getPrevOperation().isFly())
            return new Operation(echoForExtremas);
        else {
            transition();
            if(currSubState == SubState.FINDEXTREMATWO)
                return new Operation(Action.FLYFORWARD);
            else
                return new Operation(turnForExtremas);
        }
    }

    private Operation navigatingToIsland() {
        if(decisionMaker.getPrevOperation().isEchoFwd()) {
            for (int i = 0; i < decisionMaker.getMap().getDistFront(); i++)
                operations.add(new Operation(Action.FLYFORWARD));
        }
        transition();
        if(finished)
            return new Operation(Action.SCAN);
        else
            return operations.remove();
    }

    public State getName() {
        return this.stateName;
    }

    public Boolean isFinished() {
        return this.finished;
    }

}
