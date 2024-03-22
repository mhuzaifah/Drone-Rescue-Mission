package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanResponse extends Response  {

    private ArrayList<String> biomes;
    private ArrayList<String> creeks;
    private ArrayList<String> sites;

    ScanResponse(JSONObject responseInfo, Operation prevOperation) {
        super(responseInfo, prevOperation);
        this.biomes = parseBiomes(super.getExtras().getJSONArray("biomes"));
        this.creeks = parseCreeks(super.getExtras().getJSONArray("creeks"));
        this.sites = parseSites(super.getExtras().getJSONArray("sites"));
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

    public ArrayList<String> parseSites(JSONArray sitesJSONArray) {
        ArrayList<String> sites = new ArrayList<String>();
        for(Object siteObj : sitesJSONArray) {
            sites.add(siteObj.toString());
        }

        return sites;
    }

    public ArrayList<String> getBiomes() {
        return this.biomes;
    }

    public ArrayList<String> getCreeks() {
        return this.creeks;
    }

    public ArrayList<String> getSites() {
        return this.sites;
    }

}
