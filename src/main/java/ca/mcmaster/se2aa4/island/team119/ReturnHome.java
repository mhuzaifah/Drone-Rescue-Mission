package ca.mcmaster.se2aa4.island.team119;

public class ReturnHome implements SearchState{

    ReturnHome(){}

    @Override
    public Operation handle() {
        return new Operation(Action.SCAN);
    }

    @Override
    public void transition() {}

    @Override
    public Boolean isFinished() {
        return null;
    }

    @Override
    public State getName() {
        return null;
    }

}
