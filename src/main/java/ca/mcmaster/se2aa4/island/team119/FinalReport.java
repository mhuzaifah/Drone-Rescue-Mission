package ca.mcmaster.se2aa4.island.team119;

public class FinalReport {

    Map map;

    FinalReport(Map map) {
        this.map = map;
    }

    public String getReport() {
        try{
            return map.findClosestCreek().getId();
        }
        catch (IndexOutOfBoundsException e){
            return "No creek found";
        }
    }

}