// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// ReturnHome
// return home search state
package ca.mcmaster.se2aa4.island.team119;

public class ReturnHome implements SearchState{

    DecisionHandler decisionHandler;
    private final SearchStateName name = SearchStateName.SEARCHISLAND;
    private Boolean finished = false;

    ReturnHome(DecisionHandler decisionHandler) {
        this.decisionHandler = decisionHandler;
    }

    // returns a scan operation
    @Override
    public Operation handle() {
        this.finished = true;
        return new Operation(Action.STOP);
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
