package ca.mcmaster.se2aa4.island.team119;

public class Operation {

    private Action action;
    Operation(Action action) {
        this.action = action;
    }

    public boolean isEchoLeft() {
        return this.action == Action.ECHOLEFT;
    }

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
    public Action getAction() {
        return this.action;
    }

}
