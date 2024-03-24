// Muhammad Huzaifah, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Drone Rescue Mission
// The ScanResponse class represents the response received from the drone's Scan action.
// This class extends the abstract Response class and contains information about the biomes, creeks, and the emergency sites
// which can be accessed using the class's getter methods

package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanResponse extends Response  {

    private ArrayList<String> biomes; // List of biomes detected by the drone during scanning
    private ArrayList<String> creeks; // List of creeks detected by the drone during scanning (could be empty too, meaning no creak was there during that scan)
    private String site; // The emergency site (if any) detected by the drone during the scan

    // Constructs a ScanResponse object with the response information and previous operation.
        // responseInfo -- The response information containing details about the response.
        // prevOperation -- The previous operation performed by the drone.
    ScanResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
        this.biomes = parseBiomes(super.getExtras().getJSONArray("biomes"));
        this.creeks = parseCreeks(super.getExtras().getJSONArray("creeks"));
        this.site = parseSites(super.getExtras().getJSONArray("sites"));
    }

    // Parses the biomes JSON array from the response into a list of strings.
        // biomesJSONArray -- The JSON array containing information about the detected biomes.
    // Returns the list of biomes detected by the drone in that area it scanned.
    public ArrayList<String> parseBiomes(JSONArray biomesJSONArray) {
        ArrayList<String> biomes = new ArrayList<String>();
        for (Object biomeObj : biomesJSONArray) {
            biomes.add(biomeObj.toString());
        }
        return biomes;
    }

    // Parses the creeks JSON array from the response into a list of strings.
        // creeksJSONArray -- The JSON array containing information about the detected creeks (if any).
    // Returns the list of creeks detected by the drone in that area it scanned.
    public ArrayList<String> parseCreeks(JSONArray creeksJSONArray) {
        ArrayList<String> creeks = new ArrayList<String>();
        for(Object creekObj : creeksJSONArray) {
            creeks.add(creekObj.toString());
        }
        return creeks;
    }

    // Parses the emergency sites JSON array and retrieves the site if present.
        // sitesJSONArray -- The JSON array containing information about the detected emergency sites when the area was scanned.
    // Returns the emergency site detected by the drone.
    public String parseSites(JSONArray sitesJSONArray) {

        if(!sitesJSONArray.isEmpty())
            return (String) sitesJSONArray.get(0);

        return "";
    }

    // a getter method that retrieves the list of biomes detected by the drone and returns it.
    public ArrayList<String> getBiomes() {
        return this.biomes;
    }

    // a getter method that retrieves the list of creeks detected by the drone and returns it.
    public ArrayList<String> getCreeks() {
        return this.creeks;
    }

    // a getter method that retrieves the emergency site detected by the drone and returns it (the string).
    public String getSite() {
        return this.site;
    }

}
