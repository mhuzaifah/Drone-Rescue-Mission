// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// ReturnHome
// return home search state
package ca.mcmaster.se2aa4.island.team119;

public class ReturnHome implements SearchState{

    ReturnHome(){}

    // returns a scan operation
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
