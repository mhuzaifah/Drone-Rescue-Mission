package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public interface SearchState {
    Operation handle();
    Boolean isFinished();
    SearchStateName getName();

}
