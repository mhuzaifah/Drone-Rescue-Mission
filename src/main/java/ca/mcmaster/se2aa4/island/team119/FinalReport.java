package ca.mcmaster.se2aa4.island.team119;

public class FinalReport {

    Map map;

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