// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The movementResponse class is there for future extensionabillity.
// Since the other types of responses have their own response class like scan and echo, movement therefore should have one too.
// If in the future a movement action gives some information as extras, then it will be easier to implement those changes

package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class MovementResponse extends Response {
    // Constructs a MovementResponse object with the provided response information we get after an operation is executed, and the previous operation.
        // responseInfo -- The response information is the information you get after an operation is done which in this case will contain any details about the movement of the drone (flying or heading).
        // prevOperation -- The previous operation performed by the drone.
    MovementResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
    }
}
