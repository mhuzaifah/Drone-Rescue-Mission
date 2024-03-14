package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

import java.io.StringReader;
import java.util.Objects;
import org.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONTokener;
public class PhotoScanner {

    private final Logger logger = LogManager.getLogger();

    PhotoScanner() {
        //this.drone = drone;
    }

    public boolean scannedBiomes(String response) {
        JSONObject jsonResponse = new JSONObject(new JSONTokener(new StringReader(response)));
        JSONObject extras = jsonResponse.getJSONObject("extras");

        // Get the list of biomes from the response
        JSONArray biomesArray = extras.getJSONArray("biomes");

        boolean isOceanPresent = biomesArray.toList().contains("OCEAN");

        logger.info("Biomes: " + biomesArray);

        return isOceanPresent;
    }



    /*public PhotoScanner() {
        // Initialization logic
    }

    public String scanGround() {
        // scan the ground underneath the drone
        // Return information about inlets and emergency location
        // save the returned information about inlets and emergency location into a list
        return "Ground scan result";
    }*/
}
