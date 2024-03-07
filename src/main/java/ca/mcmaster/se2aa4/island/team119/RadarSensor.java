package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

import java.io.StringReader;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONTokener;

public class RadarSensor {
    private final Logger logger = LogManager.getLogger();
    //private final Drone drone;

    RadarSensor(Drone drone) {
        //this.drone = drone;
    }

    private enum ScanResult{
        GROUND,
        OUT_OF_RANGE
    }
    //JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
    //JSONObject extraInfo = response.getJSONObject("extras");
    //String echoResult;
    public boolean scanGround(JSONObject decision, Direction direction, String echoResult){
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", direction.toString()));
        //response = new JSONObject(new JSONTokener(new StringReader(s)));
        //extraInfo = response.getJSONObject("extras");
        //echoResult = extraInfo.getString("found");
        logger.info("_____________________________________________________________________________________________");
        logger.info("the decision executed is " + echoResult);
        return Objects.equals(echoResult, "GROUND");
    }
}
//consider a blank echoResult ""
