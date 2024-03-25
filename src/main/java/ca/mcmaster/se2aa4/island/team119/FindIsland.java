package ca.mcmaster.se2aa4.island.team119;

import java.util.ArrayDeque;
import java.util.Queue;

public class FindIsland implements SearchState {

    private final SearchStateName name = SearchStateName.FINDISLAND;
    private MissionCoordinator missionCoordinator;
    private SubState currSubState;
    private Boolean finished = false;
    private Boolean islandInitInFront;
    private Boolean updatedExtremaOperations = false;
    private Action echoForExtremas;
    private Action turnForExtremas;
    private Queue<Operation> operations;

    FindIsland(MissionCoordinator missionCoordinator) {
        this.missionCoordinator = missionCoordinator;
        this.currSubState = SubState.CHECKSURROUNDINGS;
        this.operations = new ArrayDeque<>();
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

    public void transitionLogic() {
        switch (currSubState) {
            case CHECKSURROUNDINGS -> {
                islandInitInFront = missionCoordinator.getMap().inFront().sameTileType(new MapTile("GROUND"));
                currSubState = SubState.FINDEXTREMAONE;
            }
            case FINDEXTREMAONE-> {
                if (!missionCoordinator.getMap().toLeft().sameTileType(new MapTile("GROUND")) && missionCoordinator.getMap().toRight().sameTileType(new MapTile("OCEAN")))
                    currSubState = SubState.FINDEXTREMATWO;
                else if (!missionCoordinator.getMap().toRight().sameTileType(new MapTile("GROUND")) && missionCoordinator.getMap().toLeft().sameTileType(new MapTile("OCEAN")))
                    currSubState = SubState.FINDEXTREMATWO;
            }
            case FINDEXTREMATWO -> {
                if (missionCoordinator.getMap().toLeft().sameTileType(new MapTile("GROUND")) || missionCoordinator.getMap().toRight().sameTileType(new MapTile("GROUND"))) {
                    currSubState = SubState.NAVIGATETOISLAND;
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
        if(missionCoordinator.getPrevOperation() == null) {
            return new Operation(Action.ECHORIGHT);
        }
        else if (missionCoordinator.getPrevOperation().isEchoRight()) {
            return new Operation(Action.ECHOLEFT);
        }
        else if (missionCoordinator.getPrevOperation().isEchoLeft()) {
            return new Operation(Action.ECHOFORWARD);
        }
        else {
            transitionLogic();
            Integer distanceLeft = missionCoordinator.getMap().getDistLeft();
            Integer distanceRight = missionCoordinator.getMap().getDistRight();
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
        if(missionCoordinator.getPrevOperation().isEcho()) {
            transitionLogic();
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

        if(missionCoordinator.getPrevOperation().isFly())
            return new Operation(echoForExtremas);
        else {
            transitionLogic();
            if(currSubState == SubState.FINDEXTREMATWO)
                return new Operation(Action.FLYFORWARD);
            else
                return new Operation(turnForExtremas);
        }
    }

    private Operation navigatingToIsland() {
        if(missionCoordinator.getPrevOperation().isEchoFwd()) {
            for (int i = 0; i < missionCoordinator.getMap().getDistFront(); i++)
                operations.add(new Operation(Action.FLYFORWARD));
        }
        transitionLogic();
        if(finished)
            return new Operation(Action.SCAN);
        else
            return operations.remove();
    }

    public Boolean isFinished() {
        return this.finished;
    }

    @Override
    public SearchStateName getName() {
        return this.name;
    }

}
