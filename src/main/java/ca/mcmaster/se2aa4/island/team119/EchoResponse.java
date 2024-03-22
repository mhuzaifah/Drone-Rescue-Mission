package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class EchoResponse extends Response  {

    private Integer range;
    private String found;

    EchoResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
        this.range = super.getExtras().getInt("range");
        this.found = super.getExtras().getString("found");
    }

    public Integer getRange() {
        return this.range;
    }

    public String getFound() {
        return this.found;
    }

}
