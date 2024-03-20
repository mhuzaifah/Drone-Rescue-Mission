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

}
