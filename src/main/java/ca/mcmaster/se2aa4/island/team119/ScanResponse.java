package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanResponse extends Response  {

    private ArrayList<String> biomes;
    private ArrayList<String> creeks;
    private String site;

    ScanResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
        this.biomes = parseBiomes(super.getExtras().getJSONArray("biomes"));
        this.creeks = parseCreeks(super.getExtras().getJSONArray("creeks"));
        this.site = parseSites(super.getExtras().getJSONArray("sites"));
    }

    public ArrayList<String> parseBiomes(JSONArray biomesJSONArray) {
        ArrayList<String> biomes = new ArrayList<String>();
        for (Object biomeObj : biomesJSONArray) {
            biomes.add(biomeObj.toString());
        }
        return biomes;
    }

    public ArrayList<String> parseCreeks(JSONArray creeksJSONArray) {
        ArrayList<String> creeks = new ArrayList<String>();
        for(Object creekObj : creeksJSONArray) {
            creeks.add(creekObj.toString());
        }

        return creeks;
    }

    public String parseSites(JSONArray sitesJSONArray) {

        if(!sitesJSONArray.isEmpty())
            return (String) sitesJSONArray.get(0);

        return "";
    }

    public ArrayList<String> getBiomes() {
        return this.biomes;
    }

    public ArrayList<String> getCreeks() {
        return this.creeks;
    }

    public String getSite() {
        return this.site;
    }

}
