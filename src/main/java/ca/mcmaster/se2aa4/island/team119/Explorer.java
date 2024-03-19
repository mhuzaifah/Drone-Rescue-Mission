package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    int counter = 0;
    Drone drone;
    Map map;
    InfoTranslator translator;
    ResultProcessor resultProcessor = new ResultProcessor();

    //Variables used to execute exploring logic. Will need to be refactored and encapsulated somewhere else eventually
    /*
        echoResult - Keeps track of whether echo returned ground or OUT_OF_RANGE
        decisionExecuted - Keeps track of the previous decision made by the drone
        intialEchoExecuted - For the first initial echo in order to store the maximum distance that can be travelled
     */
    public String echoResult = "";
    public String creek = "";
    public boolean creekFound = false;
    public String site = "";
    String decisionExecuted = "";
    int maxDistance = -1;
    boolean intialEchoExecuted = false;
    int decisioncount = 0;


    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        translator = new InfoTranslator();
        logger.info("** Initialization info:\n {}",info.toString(2));
        Information initInfo = translator.parse(info);
        drone = new Drone(initInfo.retreive(InfoType.HEADING, Direction.class), initInfo.retreive(InfoType.BUDGET, Integer.class));
        map = new Map(drone);
        logger.info("The drone is facing {}", drone.direction);
        logger.info("Battery level is {}", drone.batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        drone.explore(decision, echoResult);
        decisionExecuted = decision.getString("action");
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        Information parsedResponse = translator.parse(response);

        logger.info("** Response received:\n"+response.toString(2));

        Integer cost = parsedResponse.retreive(InfoType.COST, Integer.class);
        logger.info("The cost of the action was {}", cost);

        drone.batteryLevel -= cost;
        logger.info("The battery of the drone is {}", drone.batteryLevel);

        String status = parsedResponse.retreive(InfoType.STATUS, String.class);
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = parsedResponse.retreive(InfoType.EXTRAS, JSONObject.class);

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

        try{
            creek = extraInfo.getJSONArray("creeks").getString(0);
            logger.info("Creek {}", creek);
        }
        catch(Exception e){
            logger.info("no creek found");
        }

        //site = response.getString("sites");

        if (!Objects.equals(creek, "")){
            creekFound = true;
        }

        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(creekFound, creek);
        logger.info(finalReport.getReport());
        return finalReport.getReport();
    }

}