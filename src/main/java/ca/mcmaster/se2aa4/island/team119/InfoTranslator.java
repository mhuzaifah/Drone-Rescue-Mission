package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class InfoTranslator {

    InfoTranslator() {}

    public Response createResponse(JSONObject responseInfo, Operation prevOperation) {
        switch (prevOperation.getAction()){
            case SCAN -> {
                LogManager.getLogger().info("Making SCAN RESPONSE");
                return new ScanResponse(responseInfo, prevOperation);
            }
            case ECHOFORWARD, ECHORIGHT, ECHOLEFT -> {
                LogManager.getLogger().info("Making ECHO RESPONSE");
                return new EchoResponse(responseInfo, prevOperation);
            }
            case FLYFORWARD, FLYRIGHT, FLYLEFT, STOP -> {
                LogManager.getLogger().info("Making MOVEMENT RESPONSE");
                return new MovementResponse(responseInfo, prevOperation);
            }
        }
        return null;
    }

//    public Response parse(JSONObject jsonInfo) {
//        Response result = new Response(jsonInfo.getInt("cost"), jsonInfo.getJSONObject("extras"), jsonInfo.getString("status"));
//        return result;
//    }

}
