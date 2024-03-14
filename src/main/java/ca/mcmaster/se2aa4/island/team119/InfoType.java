package ca.mcmaster.se2aa4.island.team119;

import eu.ace_design.island.game.actions.Heading;

public enum InfoType {

    HEADING("heading"),
    BUDGET("budget"),
    COST("cost"),
    EXTRAS("extras"),
    STATUS("status");


    private final String abreviation;

    InfoType(String abv) {
        this.abreviation = abv;
    }

    @Override
    public String toString() {
        return this.abreviation;
    }

    public Boolean matches(String str) {
        return str.equals(this.abreviation);
    }

    public Boolean equals(InfoType type) {
        return type == this;
    }

}
