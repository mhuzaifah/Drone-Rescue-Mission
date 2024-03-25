// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Action
// enum of the drone's possible actions
package ca.mcmaster.se2aa4.island.team119;

public enum Action {

    SCAN("scan"),
    ECHOFORWARD("echo", "forward"),
    ECHORIGHT("echo", "right"),
    ECHOLEFT("echo", "left"),
    FLYFORWARD("fly"),
    FLYRIGHT("heading", "right"),
    FLYLEFT("heading", "left"),
    STOP("stop");

    private String abreviation;
    private String direction;

    Action(String abv, String direc) {
        this.abreviation = abv;
        this.direction = direc;
    }

    Action(String abv) {
        this.abreviation = abv;
    }

    // returns the string of the action
    @Override
    public String toString() {
        return this.abreviation;
    }

    // returns the opposite action as type Action
    public Action oppositeAction() {
        Action oppositeAction = null;
        switch (this) {
            case SCAN -> { oppositeAction = SCAN; }
            case ECHOFORWARD -> { oppositeAction = ECHOFORWARD; }
            case ECHORIGHT -> { oppositeAction = ECHOLEFT; }
            case ECHOLEFT -> { oppositeAction = ECHORIGHT; }
            case FLYFORWARD -> { oppositeAction = FLYFORWARD; }
            case FLYRIGHT -> { oppositeAction = FLYLEFT; }
            case FLYLEFT -> { oppositeAction = FLYRIGHT; }
            case STOP -> { oppositeAction = STOP; }
        }
        return oppositeAction;
    }
}
