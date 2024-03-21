package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class FindIsland implements SearchState {

    private DecisionMaker decisionMaker;
    private SubState currSubState;
    private final State stateName = State.FINDISLAND;
    private Boolean finished = false;
    private Boolean islandInFront;
    private Integer distanceRight;
    private Integer distanceLeft;
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
                if(decisionMaker.getPrevOperation() == null)
                    return new Operation(Action.ECHORIGHT);
                else if (decisionMaker.getPrevOperation().isEchoRight())
                    return new Operation(Action.ECHOLEFT);
                else if (decisionMaker.getPrevOperation().isEchoLeft()) {
                    return new Operation(Action.ECHOFORWARD);
                }
                else {
                    transition();
                    if (islandInFront) {
                        if (decisionMaker.getMap().getDistLeft() <= decisionMaker.getMap().getDistRight()) {
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
            case FINDEXTREMAONE -> {
                //Finding an extreme point of the island
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
            case FINDEXTREMATWO -> {
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
            case NAVIGATETOISLAND -> {
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
            default -> throw new IllegalStateException("Unexpected value: " + currSubState);
        }
    }

    @Override
    public void transition() {
        switch (currSubState) {
            case CHECKSURROUNDINGS -> {
                islandInFront = decisionMaker.getMap().inFront().sameTileType(new MapTile("GROUND"));
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

    public State getName() {
        return this.stateName;
    }

    public Boolean isFinished() {
        return this.finished;
    }

}
