// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The Response class represents the response received from the drone after each operation is done.
// This abstract class provides a framework for handling different types of responses
// such as scan, echo, and movement responses. It encapsulates information about
// the cost, status, and additional extras of a response, and defines getter methods to access this information.

package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public abstract class Response {

    private Integer cost; // The cost of the operation/action
    private JSONObject extras; // Additional extras included in the response
    private String status; // The status of the drone given in the response
    private ResultType type; // The result type (how the response is formatted) based on the action/operation performed

    // Constructs a Response object with the provided response information we get after an operation is executed, and the previous operation.
            // responseInfo -- The response information is the information you get after an operation is done
            // prevOperation -- The previous operation performed by the drone.
    Response(JSONObject responseInfo, Operation prevOperation) {
        this.cost = responseInfo.getInt("cost");
        this.status = responseInfo.getString("status");
        this.extras = responseInfo.getJSONObject("extras");
        setType(prevOperation);
    }

    // Sets the type of the response based on the operation performed by the drone.
        // prevOperation The operation performed by the drone that we have now got the response of.
    private void setType(Operation prevOperation) {
        switch (prevOperation.getAction()) {
            case SCAN -> { this.type = ResultType.SCANRESULT; }
            case ECHOFORWARD -> { this.type = ResultType.ECHOFWDRESULT; }
            case ECHORIGHT -> { this.type = ResultType.ECHORIGHTRESULT; }
            case ECHOLEFT -> { this.type = ResultType.ECHOLEFTRESULT; }
            case FLYFORWARD -> { this.type = ResultType.FLYFWDRESULT; }
            case FLYRIGHT -> { this.type = ResultType.FLYRIGHTRESULT; }
            case FLYLEFT -> { this.type = ResultType.FLYLEFTRESULT; }
            case STOP -> { this.type = ResultType.STOPRESULT; }
        }
    }

    // getType is a getter method that retrieves the type fo the response
    // and returns the type of the response
    public ResultType getType() {
        return this.type;
    }

    // getCost is a getter method that retrieves the cost of an action from the response
    // and returns the cost as an integer
    public Integer getCost() {
        return this.cost;
    }

    // getExtras is a getter method that retrieves the additional extras included in the response.
    // Returns the additional extras included in the response as a JSONObject.
    protected JSONObject getExtras() {
        return this.extras;
    }

    // getStatus is a getter method that retrieves the status from the response.
    // returns the status from the response as a String.
    public String getStatus() {
        return this.status;
    }
}

