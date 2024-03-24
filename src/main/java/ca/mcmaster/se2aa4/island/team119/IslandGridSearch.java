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
        GETBACKTOISLAND,
        LOOPBACKSETUP,
        LOOPBACKODDCASE,
        LOOPBACKEVENCASE
    }
    private Queue<Operation> operations;
    private Operation turn;
    private Operation echoForTurn;
    private Boolean checkedTurnEdgeCase = false;
    private Boolean loopingBack = false;

    IslandGridSearch(DecisionMaker decisionMaker) {
        this.decisionMaker = decisionMaker;
        this.currSubState = SubState.EXPLORE;
        this.operations = new ArrayDeque<>();
        setDirectionOfInterest();
        operations.add(new Operation(Action.ECHOFORWARD));
    }

    @Override
    public Operation handle() {
        return search();
    }

    @Override
    public Operation search() {
        setDirectionOfInterest();
        transition();
        return operations.remove();
    }

    @Override
    public void transition() {
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
    }

    private void explore() {
        if(decisionMaker.getPrevOperation().isEchoFwd()) {
            if (decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
                getReadyForTurn();
            }
            else {
                moveForwardInExploration();
            }
        }
    }

    private void getReadyForTurn() {
        if(decisionMaker.getMap().getDistFront() > 2)
            flyFwdAndScan(2);
        currSubState = SubState.TURN;
        operations.add(echoForTurn);
    }

    private void moveForwardInExploration() {
        Integer distInFront = decisionMaker.getMap().getDistFront();
        MapTile tileInFront = decisionMaker.getMap().inFront();
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
        Operation prevOperation = decisionMaker.getPrevOperation();
        if(prevOperation.isEchoRight() || prevOperation.isEchoLeft()) {
            turnAtCorrectTime();
        }
        else if(prevOperation.isEchoFwd()) {
            returnExplorationOrLoopBack();
        }
    }

    private void turnAtCorrectTime() {
        Operation prevOperation = decisionMaker.getPrevOperation();
        Integer distance = prevOperation.isEchoRight() ? decisionMaker.getMap().getDistRight() : decisionMaker.getMap().getDistLeft();
        MapTile tileToEchoSide = prevOperation.isEchoRight() ? decisionMaker.getMap().toRight() : decisionMaker.getMap().toLeft();
        if (distance == 0) {
            operations.add(new Operation(Action.FLYFORWARD));
            operations.add(echoForTurn);
        }
        else {
            executeTurn(turn);
            operations.add(new Operation(Action.ECHOFORWARD));
        }
    }

    private void returnExplorationOrLoopBack() {
        if(decisionMaker.getMap().inFront().sameTileType(new MapTile("OCEAN"))) {
            if(!loopingBack) {
                currSubState = SubState.LOOPBACKSETUP;
                loopingBack = true;
                setDirectionOfInterest();
                operations.add(echoForTurn);
            }
            else
                operations.add(new Operation(Action.STOP));
        }
        else {
            currSubState = SubState.GETBACKTOISLAND;
            operations.add(new Operation(Action.ECHOFORWARD));
        }
    }

    private void getBackToIsland() {
        if(decisionMaker.getPrevOperation().isEchoFwd()) {
            Integer distInFront = decisionMaker.getMap().getDistFront();
            for (int i = 0; i < distInFront - 1; i++) {
                operations.add(new Operation(Action.FLYFORWARD));
            }
            operations.add(new Operation(Action.SCAN));
            operations.add(new Operation(Action.ECHOFORWARD));
            currSubState = SubState.EXPLORE;
        }
    }

    private void loopBackSetup() {
        if (decisionMaker.getPrevOperation().isEcho()) {
            Integer dist = decisionMaker.getPrevOperation().isEchoLeft() ? decisionMaker.getMap().getDistLeft() : decisionMaker.getMap().getDistRight();

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
        if (decisionMaker.getPrevOperation().isEchoFwd()) {
            MapTile tileInFront = decisionMaker.getMap().inFront();

            if (!tileInFront.sameTileType(new MapTile("GROUND")))
                moveSideways(2);

            operations.add(new Operation(Action.SCAN));
            operations.add(new Operation(Action.ECHOFORWARD));
            currSubState = SubState.EXPLORE;
        }
    }

    private void setDirectionOfInterest() {
        Direction droneHeading = decisionMaker.getDrone().getHeading();
        Direction startingEdge = decisionMaker.getMap().getStartingEdge();

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
        operations.add(new Operation(Action.FLYFORWARD));
        operations.add(new Operation(Action.SCAN));
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
    public State getName() {
        return this.stateName;
    }

}