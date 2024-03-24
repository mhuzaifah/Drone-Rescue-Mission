// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Operation
// check what the action is

package ca.mcmaster.se2aa4.island.team119;

public class Operation {

    private Action action;
    Operation(Action action) {
        this.action = action;
    }

    // these methods check what the action is
    // return true if the actions are as specified, false, if not
    public boolean isEchoLeft() {return this.action == Action.ECHOLEFT;}

    public boolean isEchoRight() {
        return this.action == Action.ECHORIGHT;
    }

    public boolean isEchoFwd() {
        return this.action == Action.ECHOFORWARD;
    }

    public boolean isEcho() {
        return this.action == Action.ECHOFORWARD || this.action == Action.ECHORIGHT || this.action == Action.ECHOLEFT;
    }

    public boolean isFly() {
        return this.action == Action.FLYFORWARD || this.action == Action.FLYRIGHT || this.action == Action.FLYLEFT;
    }

    public boolean isScan() {
        return this.action == Action.SCAN;
    }

    public boolean isFlyFwd() { return this.action == Action.FLYFORWARD; }

    // returns the action
    public Action getAction() {
        return this.action;
    }

}
