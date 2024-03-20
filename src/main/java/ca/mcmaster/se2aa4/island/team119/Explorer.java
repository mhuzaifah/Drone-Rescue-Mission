package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.apache.regexp.RE;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    public final Logger logger = LogManager.getLogger();

    private Drone drone;
    private Map map;
    private DecisionMaker decisionMaker;
    private InfoTranslator translator;
    private ca.mcmaster.se2aa4.island.team119.Response information;
    private ca.mcmaster.se2aa4.island.team119.Response Response;

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

        Response response = new Response(responseInfo, decisionMaker.getPrevOperation());
        map.update(response, drone.getHeading());
        drone.update(response);

        logger.info("The battery of the drone is {}", drone.getBattery());
        // logger.info("The cost of the action was {}", information.getCost());
        // logger.info("The status of the drone is {}", information.getStatus());

//        //Checking if we echoed, and if we did then getting the max distance (only for first forward echo) and storing echo results
//        if(decisionExecuted.equals("echo")) {
//            echoResult = extraInfo.getString("found");
//
//            if(!intialEchoExecuted) {
//                logger.info("GETTING MAX RANGE {}", extraInfo.getInt("range"));
//                maxDistance = extraInfo.getInt("range");
//                intialEchoExecuted = true;
//            }
//
//        } else {
//            echoResult = "";
//        }
//
//        try{
//            creek = extraInfo.getJSONArray("creeks");
//            logger.info("Creek {}", creek);
//        }
//        catch(Exception e){
//            logger.info("no creek found");
//        }

        logger.info("Additional information received: {}", responseInfo.get("extras"));
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(creekFound, creek);
        logger.info(finalReport.getReport());
        return finalReport.getReport();
    }

}