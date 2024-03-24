// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone
// holds info about drone object and returns commands for movements

package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class Drone {
    private Direction heading;
    private Integer batteryLevel;

    Drone(String direction, Integer battery) {
        this.heading = Direction.toDirection(direction);
        this.batteryLevel = battery;
    }

    // returns the JSON object for scanning command
    public JSONObject scan() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "scan");
        return cmd;
    }

    // returns the JSON object for echoForward command
    public JSONObject echoFwd() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    // returns the JSON object for echoRight command
    public JSONObject echoRight() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.lookRight().toString()));
        return cmd;
    }

    // returns the JSON object for echoLeft command
    public JSONObject echoLeft() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.lookLeft().toString()));
        return cmd;
    }

    // returns the JSON object for flyForward command
    public JSONObject flyFwd() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "fly");
        return cmd;
    }

    // returns the JSON object for flyRight command
    public JSONObject flyRight() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "heading");
        this.heading = heading.lookRight();
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    // returns the JSON object for flyLeft command
    public JSONObject flyLeft() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "heading");
        this.heading = heading.lookLeft();
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    // returns the JSON object for stop command
    public JSONObject stop() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "stop");
        return cmd;
    }

    // updates the battery level based on the cost of the decision
    // parameter - response, a Response object, that holds the response received after an action
    public void update(Response response) {
        Integer cost = response.getCost();
        this.batteryLevel -= cost;
    }

    // getter for direction of that the drone is heading
    // returns heading as type Direction(enum)
    public Direction getHeading() {
        return this.heading;
    }

    // getter for the drones battery level
    // returns the battery as an Integer
    public Integer getBattery() {
        return this.batteryLevel;
    }

}

