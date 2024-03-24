package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class MovementResponse extends Response {
    MovementResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
    }
}
