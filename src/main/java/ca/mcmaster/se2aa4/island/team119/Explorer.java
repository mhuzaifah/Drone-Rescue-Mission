package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;

import jdk.javadoc.doclet.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private Integer decisionCounter = 0;

    private final Logger logger = LogManager.getLogger();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        MapParser mapParser = new MapParser();
        Drone drone = new Drone(info);
        logger.info("The drone is facing {}", drone.direction);
        logger.info("Battery level is {}", drone.batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        if (decisionCounter < 102) {
            if (decisionCounter % 2 == 0) {
                decision.put("action", "scan");
            } else {
                decision.put("action", "fly");
            }
        }
        else {
            decision.put("action", "stop");
        }

        decisionCounter++;

        logger.info("** Decision: {}",decision.toString());

        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport();
        return finalReport.getReport();
    }

}
