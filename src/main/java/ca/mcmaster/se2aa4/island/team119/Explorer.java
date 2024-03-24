package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Map map;
    private DecisionMaker decisionMaker;
    private InfoTranslator translator;
    int decisioncount = 0;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject initInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        translator = new InfoTranslator();
        logger.info("** Initialization info:\n {}",initInfo.toString(2));
        map = new Map();
        drone = new Drone(initInfo.getString("heading"), initInfo.getInt("budget"));
        decisionMaker = new DecisionMaker(drone, map);
        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        // refactor to use battery threshold
        if(drone.getBattery() > 100) {
            try {
                decisioncount++;
                JSONObject decision = decisionMaker.makeDecision();
                logger.info("** Decision: {}", decision.toString());
                return decision.toString();
            } catch (IllegalArgumentException e) {
                //Refactor this catch block
                logger.info("ERROR: {}", e.getMessage());
                return null;
            }
        }
        else{
            if(decisioncount == 0) {
                decisioncount++;
                return new JSONObject().put("action", "scan").toString();
            }
            else {
                return new JSONObject().put("action", "stop").toString();
            }
        }
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject responseInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+ responseInfo.toString(2));

        Response response = translator.createResponse(responseInfo, decisionMaker.getPrevOperation());
        map.update(response, drone.getHeading());
        drone.update(response);

        logger.info("The battery of the drone is {}", drone.getBattery());
        logger.info("Additional information received: {}", response.getExtras());
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(map);
        logger.info(finalReport.getReport());
        return finalReport.getReport();
    }

}