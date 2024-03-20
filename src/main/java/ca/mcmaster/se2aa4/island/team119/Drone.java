package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class Drone {
    private Direction heading;
    private Integer batteryLevel;

    Drone(String direction, Integer battery) {
        this.heading = Direction.toDirection(direction);
        this.batteryLevel = battery;
    }

    public JSONObject scan() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "scan");
        return cmd;
    }

    public JSONObject echoFwd() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    public JSONObject echoRight() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.lookRight().toString()));
        return cmd;
    }

    public JSONObject echoLeft() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");
        cmd.put("parameters", new JSONObject().put("direction", this.heading.lookLeft().toString()));
        return cmd;
    }

    public JSONObject flyFwd() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "fly");
        return cmd;
    }

    public JSONObject flyRight() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "heading");
        this.heading = heading.lookRight();
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    public JSONObject flyLeft() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "heading");
        this.heading = heading.lookLeft();
        cmd.put("parameters", new JSONObject().put("direction", this.heading.toString()));
        return cmd;
    }

    public JSONObject stop() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "stop");
        return cmd;
    }

    public void update(Response response) {
        Integer cost = response.getCost();
        this.batteryLevel -= cost;
    }

    public Direction getHeading() {
        return this.heading;
    }

    public Integer getBattery() {
        return this.batteryLevel;
    }

}

