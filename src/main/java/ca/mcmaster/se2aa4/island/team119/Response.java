package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Response {

    private final Logger logger = LogManager.getLogger();
    private Integer cost;
    private JSONObject extras;
    private String status;
    private ResultType type;

    Response(JSONObject responseInfo, Operation prevOperation) {
        logger.info("CREATING RESPONSE OBJECT");
        this.cost = responseInfo.getInt("cost");
        this.status = responseInfo.getString("status");
        this.extras = responseInfo.getJSONObject("extras");
        setType(prevOperation);
        logger.info("CREATED RESPONSE OBJECT");
    }

    private void setType(Operation prevOperation) {
        switch (prevOperation.getAction()) {
            case SCAN -> { this.type = ResultType.SCANRESULT; }
            case ECHOFORWARD -> { this.type = ResultType.ECHOFWDRESULT; }
            case ECHORIGHT -> { this.type = ResultType.ECHORIGHTRESULT; }
            case ECHOLEFT -> { this.type = ResultType.ECHOLEFTRESULT; }
            case FLYFORWARD -> { this.type = ResultType.FLYFWDRESULT; }
            case FLYRIGHT -> { this.type = ResultType.FLYRIGHTRESULT; }
            case FLYLEFT -> { this.type = ResultType.FLYLEFTRESULT; }
        }
    }

    public ResultType getType() {
        return this.type;
    }

    public Integer getCost() {
        return this.cost;
    }

    protected JSONObject getExtras() {
        return this.extras;
    }
}

