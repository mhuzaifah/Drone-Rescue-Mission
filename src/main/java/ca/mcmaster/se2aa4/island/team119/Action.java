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

    String abreviation;
    String direction;

    Action(String abv, String direc) {
        this.abreviation = abv;
        this.direction = direc;
    }

    Action(String abv) {
        this.abreviation = abv;
    }

    @Override
    public String toString() {
        return this.abreviation;
    }

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
