package ca.mcmaster.se2aa4.island.team119;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private MissionCoordinator missionCoordinator;
    private InfoTranslator translator;
    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject initInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        translator = new InfoTranslator();
        logger.info("** Initialization info:\n {}",initInfo.toString(2));
        missionCoordinator = new MissionCoordinator(new Drone(initInfo.getString("heading"), initInfo.getInt("budget")), new Map());
    }

    @Override
    public String takeDecision() {
        JSONObject decision = missionCoordinator.makeDecision();
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject responseInfo = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+ responseInfo.toString(2));
        Response response = translator.createResponse(responseInfo, missionCoordinator.getPrevOperation());
        missionCoordinator.updateMap(response);
        missionCoordinator.updateDrone(response);
    }

    @Override
    public String deliverFinalReport() {
        FinalReport finalReport = new FinalReport(missionCoordinator.getMap());
        return finalReport.getReport();
    }

}