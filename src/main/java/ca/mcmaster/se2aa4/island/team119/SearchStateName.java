package ca.mcmaster.se2aa4.island.team119;

public enum SearchStateName {

    FINDISLAND("findisland"),
    SEARCHISLAND("searchisland"),
    RETURNHOME("returnhome");

    private String name;

    SearchStateName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
