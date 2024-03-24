package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class InfoTranslator {

    InfoTranslator() {}

    public Response createResponse(JSONObject responseInfo, Operation prevOperation) {
        switch (prevOperation.getAction()){
            case SCAN -> {
                return new ScanResponse(responseInfo, prevOperation);
            }
            case ECHOFORWARD, ECHORIGHT, ECHOLEFT -> {
                return new EchoResponse(responseInfo, prevOperation);
            }
            case FLYFORWARD, FLYRIGHT, FLYLEFT, STOP -> {
                return new MovementResponse(responseInfo, prevOperation);
            }
        }
        return null;
    }
}
