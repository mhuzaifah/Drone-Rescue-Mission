package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public interface SearchState {
    enum State {
        FINDISLAND,
        SEARCHISLAND,
        RETURNHOME
    }
    Operation handle();
    void transition();
    Boolean isFinished();
    State getName();

}
