package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class InfoTranslator {

    InfoTranslator() {}

    // creates a response object of the appropriate type to hold response received after an action
    // returns a ScanResponse object to hold all info from scan, if the previous action was a scan
    // returns a EchoResponse object to hold all info from echo, if the previous action was a echoF, echoR, or echoL
    // returns a MovementResponse object to hold all info from movement, if the previous action was a fly, flyL, or flyR
    // parameters - a JSONObject holding all the response info from the action, and the operation that was executed
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
