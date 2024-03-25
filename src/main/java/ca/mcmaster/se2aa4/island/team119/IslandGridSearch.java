package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import java.util.ArrayDeque;
import java.util.Queue;

public class IslandGridSearch implements SearchState, SearchAlgo {

    private final SearchStateName name = SearchStateName.SEARCHISLAND;
    private MissionCoordinator missionCoordinator;
    private Boolean finished = false;
    private SubState currSubState;
    enum SubState {
        EXPLORE,
        TURN,
        GETBACKTOISLAND,
        LOOPBACKSETUP,
        LOOPBACKODDCASE,
        LOOPBACKEVENCASE
    }
    private Queue<Operation> operations;
    private Operation turn;
    private Operation echoForTurn;
    private Boolean loopingBack = false;

    IslandGridSearch(MissionCoordinator missionCoordinator) {
        this.missionCoordinator = missionCoordinator;
        this.currSubState = SubState.EXPLORE;
        this.operations = new ArrayDeque<>();
        setDirectionOfInterest();
        operations.add(new Operation(Action.ECHOFORWARD));
    }

    @Override
    public Operation handle() {
        setDirectionOfInterest();
        return search();
    }

    @Override
    public Operation search () {
        setDirectionOfInterest();
        switch (currSubState) {
            case EXPLORE -> {
                explore();
            }
            case TURN -> {
                turn();
            }
            case GETBACKTOISLAND -> {
                getBackToIsland();
            }
            case LOOPBACKSETUP -> {
                loopBackSetup();
            }
            case LOOPBACKODDCASE -> {
                loopBackOddCase();
            }
            case LOOPBACKEVENCASE -> {
                loopBackEvenCase();
            }
        }
        return operations.remove();
    }

    private void explore() {
        if(missionCoordinator.getPrevOperation().isEchoFwd()) {
            if (missionCoordinator.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                getReadyForTurn();
            }
            else {
                moveForwardInExploration();
            }
        }
    }

    private void getReadyForTurn() {
        if(missionCoordinator.getMap().getDistFront() > 2)
            flyFwdAndScan(2);
        operations.add(echoForTurn);
        currSubState = SubState.TURN;
    }

    private void moveForwardInExploration() {
        Integer distInFront = missionCoordinator.getMap().getDistFront();
        MapTile tileInFront = missionCoordinator.getMap().inFront();
        LogManager.getLogger().info("TILE IN FRONT {}", tileInFront.tileType.name());
        if(distInFront == 0 && tileInFront.sameTileType(new MapTile("GROUND"))) {
           flyFwdAndScan(1);
        }
        else {
            flyFwdAndScan(1);
            for(int i=1; i <= distInFront-2; i++)
                operations.add(new Operation(Action.FLYFORWARD));
            flyFwdAndScan(1);
        }
        operations.add(new Operation(Action.ECHOFORWARD));
    }

    private void turn() {
        Operation prevOperation = missionCoordinator.getPrevOperation();
        if(prevOperation.isEchoRight() || prevOperation.isEchoLeft()) {
            turnAtCorrectTime();
        }
        else if(prevOperation.isEchoFwd()) {
            determinePostTurnAction();
        }
    }

    private void turnAtCorrectTime() {
        Operation prevOperation = missionCoordinator.getPrevOperation();
        Integer distance = prevOperation.isEchoRight() ? missionCoordinator.getMap().getDistRight() : missionCoordinator.getMap().getDistLeft();
        if (distance == 0) {
            operations.add(new Operation(Action.FLYFORWARD));
            operations.add(echoForTurn);
        }
        else {
            executeTurn(turn);
            operations.add(new Operation(Action.ECHOFORWARD));
        }
    }

    private void determinePostTurnAction() {
        if(missionCoordinator.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
            if(!loopingBack) {
                currSubState = SubState.LOOPBACKSETUP;
                loopingBack = true;
                operations.add(echoForTurn);
            }
            else {
                finished = true;
                operations.add(new Operation(Action.SCAN));
            }
        }
        else {
            currSubState = SubState.GETBACKTOISLAND;
            operations.add(new Operation(Action.ECHOFORWARD));
        }
    }

    private void getBackToIsland() {
        if(missionCoordinator.getPrevOperation().isEchoFwd()) {
            Integer distInFront = missionCoordinator.getMap().getDistFront();
            for (int i = 0; i < distInFront - 1; i++) {
                operations.add(new Operation(Action.FLYFORWARD));
            }
            operations.add(new Operation(Action.SCAN));
            operations.add(new Operation(Action.ECHOFORWARD));
            currSubState = SubState.EXPLORE;
        }
    }

    private void loopBackSetup() {
        if (missionCoordinator.getPrevOperation().isEcho()) {
            Integer dist = missionCoordinator.getPrevOperation().isEchoLeft() ? missionCoordinator.getMap().getDistLeft() : missionCoordinator.getMap().getDistRight();

            if (dist == 0) {
                moveSideways(1);
                currSubState = SubState.LOOPBACKODDCASE;
            } else if (dist == 1) {
                moveSideways(1);
                operations.add(new Operation(Action.ECHOFORWARD));
                currSubState = SubState.LOOPBACKEVENCASE;
            } else {
                operations.add(new Operation(Action.FLYFORWARD));
                operations.add(echoForTurn);
            }
        }
    }

    private void loopBackOddCase() {
        operations.add(new Operation(Action.SCAN));
        operations.add(new Operation(Action.ECHOFORWARD));
        currSubState = SubState.EXPLORE;
    }

    private void loopBackEvenCase() {
        if (missionCoordinator.getPrevOperation().isEchoFwd()) {
            MapTile tileInFront = missionCoordinator.getMap().inFront();

            if (!tileInFront.sameTileType(new MapTile("GROUND")))
                moveSideways(2);

            operations.add(new Operation(Action.SCAN));
            operations.add(new Operation(Action.ECHOFORWARD));
            currSubState = SubState.EXPLORE;
        }
    }

    private void setDirectionOfInterest() {
        Direction droneHeading = missionCoordinator.getDrone().getHeading();
        Direction startingEdge = missionCoordinator.getMap().getStartingEdge();

        if(startingEdge.isNorth()) {
            if(droneHeading.isEast()) {
                turn = new Operation(!loopingBack ? Action.FLYRIGHT : Action.FLYLEFT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHORIGHT : Action.ECHOLEFT);
            }
            else if(droneHeading.isWest()) {
                turn = new Operation(!loopingBack ? Action.FLYLEFT : Action.FLYRIGHT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHOLEFT : Action.ECHORIGHT);
            }
        }
        else if(startingEdge.isEast()) {
            if(droneHeading.isNorth()) {
                turn = new Operation(!loopingBack ? Action.FLYLEFT : Action.FLYRIGHT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHOLEFT : Action.ECHORIGHT);
            }
            else if(droneHeading.isSouth()) {
                turn = new Operation(!loopingBack ? Action.FLYRIGHT : Action.FLYLEFT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHORIGHT : Action.ECHOLEFT);
            }
        }
        else if(startingEdge.isSouth()) {
            if(droneHeading.isEast()) {
                turn = new Operation(!loopingBack ? Action.FLYLEFT : Action.FLYRIGHT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHOLEFT : Action.ECHORIGHT);
            }
            else if(droneHeading.isWest()) {
                turn = new Operation(!loopingBack ? Action.FLYRIGHT : Action.FLYLEFT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHORIGHT : Action.ECHOLEFT);
            }
        }
        else { // startingEdge.isWest()
            if(droneHeading.isNorth()) {
                turn = new Operation(!loopingBack ? Action.FLYRIGHT : Action.FLYLEFT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHORIGHT : Action.ECHOLEFT);
            }
            else if(droneHeading.isSouth()) {
                turn = new Operation(!loopingBack ? Action.FLYLEFT : Action.FLYRIGHT);
                echoForTurn = new Operation(!loopingBack ? Action.ECHOLEFT : Action.ECHORIGHT);
            }
        }
    }

    private void executeTurn(Operation turnDirection) {
        operations.add(turnDirection);
        operations.add(turnDirection);
    }

    private void flyFwdAndScan(int times) {
        for(int i=0; i < times; i++) {
            operations.add(new Operation(Action.FLYFORWARD));
            operations.add(new Operation(Action.SCAN));
        }
    }

    private void moveSideways(int spaces) {
        operations.add(turn);
        for(int i=0; i < spaces; i++)
            operations.add(new Operation(Action.FLYFORWARD));
        operations.add(turn);
        operations.add(turn);
        operations.add(turn);
    }

    @Override
    public Boolean isFinished() {
        return this.finished;
    }

    @Override
    public SearchStateName getName() {
        return this.name;
    }


}