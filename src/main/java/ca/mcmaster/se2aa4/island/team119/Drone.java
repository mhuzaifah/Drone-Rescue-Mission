package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class Drone {

    String direction;
    Integer batteryLevel;
    DroneController droneController = new DroneController();
    DroneSensor droneSensor = new DroneSensor();

    Drone(JSONObject info) {
        this.direction = info.getString("heading");
        this.batteryLevel = info.getInt("budget");
    }

    public void exploreMap() {
        RescueLogic rescueLogic = new RescueLogic();
        rescueLogic.takeDecision();
    }




}
