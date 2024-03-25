// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// FinalReport
// generates final report to be returned in Explorer

package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;

public class FinalReport {

    private Map map;

    FinalReport(Map map) {
        this.map = map;
    }

    // generates the final report
    // returns the ID of closest creek to the emergency site using the findClosestCreek method in Map, if one or more creeks are found, if no creeks are found it returns "No creek found"
    public String getReport() {
        try{
            return map.findClosestCreek().getId();
        }
        catch (IndexOutOfBoundsException e){
            return "No creek found";
        }
    }

}