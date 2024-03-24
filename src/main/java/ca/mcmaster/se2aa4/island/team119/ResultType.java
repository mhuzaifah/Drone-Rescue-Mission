package ca.mcmaster.se2aa4.island.team119;

// enum to hold the type of the response received
public enum ResultType {
    ECHOFWDRESULT(),
    ECHORIGHTRESULT(),
    ECHOLEFTRESULT(),
    SCANRESULT(),
    FLYFWDRESULT(),
    FLYRIGHTRESULT(),
    FLYLEFTRESULT(),
    STOPRESULT;

    ResultType() {}

    public String toString() {
        return "NULL";
    }

}
