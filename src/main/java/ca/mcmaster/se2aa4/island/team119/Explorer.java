package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Map map;
    private DecisionHandler decisionHandler;
    private InfoTranslator translator;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject initInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        translator = new InfoTranslator();
        logger.info("** Initialization info:\n {}",initInfo.toString(2));
        map = new Map();
        drone = new Drone(initInfo.getString("heading"), initInfo.getInt("budget"));
        decisionHandler = new DecisionHandler(drone, map);
        logger.info("The drone is facing {}", drone.getHeading());
        logger.info("Battery level is {}", drone.getBattery());
    }

    @Override
    public String takeDecision() {
        JSONObject decision = decisionHandler.makeDecision();
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject responseInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+ responseInfo.toString(2));
        Response response = translator.createResponse(responseInfo, decisionHandler.getPrevOperation());
        map.update(response, drone.getHeading());
        drone.update(response);
        logger.info("The battery of the drone is {}", drone.getBattery());
        logger.info("Additional information received: {}", response.getExtras());
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(map);
        return finalReport.getReport();
    }

}