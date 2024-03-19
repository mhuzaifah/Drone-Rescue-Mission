package ca.mcmaster.se2aa4.island.team119;

public class FinalReport {

    private String creek;
    private boolean creekFound;
    String finalReport = "No creek found";

    FinalReport(boolean creekFound, String creek) {
        this.creekFound = creekFound;
        this.creek = creek;
    }


    public String getReport() {
        if (creekFound){
            finalReport = "Creek found. Creek ID: "+ creek;
        }
        return this.finalReport;
    }

}
