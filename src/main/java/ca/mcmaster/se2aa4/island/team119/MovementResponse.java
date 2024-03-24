package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class MovementResponse extends Response {

    MovementResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);

        //get rid of logger statement
        LogManager.getLogger().info("In Movement RESPONSE, just CALLED SUPER CONSTRUCTOR");
    }

}
