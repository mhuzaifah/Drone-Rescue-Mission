package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    Drone drone;
    ResultProcessor resultProcessor = new ResultProcessor();

    //Variables used to execute exploring logic. Will need to be refactored and encapsulated somewhere else eventually
    /*
        echoResult - Keeps track of whether echo returned ground or OUT_OF_RANGE
        decisionExecuted - Keeps track of the previous decision made by the drone
        intialEchoExecuted - For the first initial echo in order to store the maximum distance that can be travelled
     */
    String echoResult = "";
    String decisionExecuted = "";
    int maxDistance = -1;
    boolean intialEchoExecuted = false;


    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        MapParser mapParser = new MapParser();
        drone = new Drone(info);
        logger.info("The drone is facing {}", drone.direction);
        logger.info("Battery level is {}", drone.batteryLevel);
    }

    @Override
    public String takeDecision() {

        JSONObject decision = new JSONObject();

        /*
            Logic of whether to explore the maze or stop
            Stops if island is found or max distance has been travelled
         */
        if((!echoResult.isEmpty() && echoResult.equals("GROUND")) || (drone.flyCount == maxDistance)) {
            logger.info("DRONE FLY COUNT: {}", drone.flyCount);
            logger.info("MAX DIST {}", maxDistance);
            logger.info("ECHO RESULT {}", echoResult);
            logger.info("Island found.");
            decision.put("action", "stop");
        }
        else {
            drone.explore(decision);
        }

        decisionExecuted = decision.getString("action");
        logger.info("** Decision: {}", decision.toString());

        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));

        logger.info("** Response received:\n"+response.toString(2));

        int cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);

        drone.batteryLevel -= cost;
        logger.info("The battery of the drone is {}", drone.batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");

        //Checking if we echoed, and if we did then getting the max distance (only for first forward echo) and storing echo results
        if(decisionExecuted.equals("echo")) {
            echoResult = extraInfo.getString("found");

            if(!intialEchoExecuted) {
                logger.info("GETTING MAX RANGE {}", extraInfo.getInt("range"));
                maxDistance = extraInfo.getInt("range");
                intialEchoExecuted = true;
            }

        } else {
            echoResult = "";
        }

        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport();
        return finalReport.getReport();
    }

}

