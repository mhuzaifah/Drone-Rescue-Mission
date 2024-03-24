// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The EchoResponse class represents the response received from the drone's echo operation.
// This class extends the abstract Response class and contains information about the range
// and the found status of the echo operation which can be accessed using the class's getter methods

package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class EchoResponse extends Response  {

    private Integer range; // The range of the echo operation
    private String found; // The status indicating whether Ground was found or not

    // Constructs an EchoResponse object with the provided response information we get after an operation is executed, and the previous operation.
        // responseInfo -- The response information is the information you get after an operation is done which in this case will contain details about the echo operation.
        // prevOperation -- The previous operation performed by the drone.
    EchoResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
        this.range = super.getExtras().getInt("range");
        this.found = super.getExtras().getString("found");
    }

    // getRange is a getter method that retrieves the range of the echo operation.
    // Returns the range of the echo operation.
    public Integer getRange() {
        return this.range;
    }

    // getFound is a getter method that retrieves what the drone has found after the echo indicating whether Ground was detected or not.
    // Returns the found status of the echo operation, Ground or Out of range
    public String getFound() {
        return this.found;
    }

}
