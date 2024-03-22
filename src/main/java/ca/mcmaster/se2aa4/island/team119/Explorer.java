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

    //Variables used to execute exploring logic. Will need to be refactored and encapsulated somewhere else eventually
    /*
        echoResult - Keeps track of whether echo returned ground or OUT_OF_RANGE
        decisionExecuted - Keeps track of the previous decision made by the drone
        intialEchoExecuted - For the first initial echo in order to store the maximum distance that can be travelled
     */
    public String creek = "";
    public boolean creekFound = false;
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
        LogManager.getLogger().info("UPDATING MAP");
        map.update(response, drone.getHeading());
        LogManager.getLogger().info("UPDATING DRONE");
        drone.update(response);

        logger.info("The battery of the drone is {}", drone.getBattery());

        try{
            creek = response.getExtras().getJSONArray("creeks").getString(0);
        }
        catch(Exception ignored){
        }

        if (!Objects.equals(creek, "")){
            creekFound = true;
        }

        logger.info("Additional information received: {}", response.getExtras());
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(creekFound, creek);
        for (int i = 0; i<map.creeks.size(); i++){
            logger.info((map.creeks.get(i)).coordinate);
            logger.info((map.creeks.get(i)).id);
        }
        logger.info(finalReport.getReport());
        return finalReport.getReport();
    }

}