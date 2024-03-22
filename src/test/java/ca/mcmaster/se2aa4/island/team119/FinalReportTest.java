package ca.mcmaster.se2aa4.island.team119;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalReportTest {

    public void setUp() {

    }

    public void getReport() {
        FinalReport finalReport = new FinalReport(true, "be398c6e-9471-4949-991d-9be788d8616e");
        assertEquals("Creek found. Creek ID: "+ "be398c6e-9471-4949-991d-9be788d8616e", finalReport.getReport());
    }
}
