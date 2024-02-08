package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class RescueLogic {

    private final Logger logger = LogManager.getLogger();
    ResultProcessor resultProcessor = new ResultProcessor();

    public String takeDecision() {
        JSONObject decision = new JSONObject();

        decision.put("action", "stop"); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());

        return decision.toString();
    }

}
