package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalReportTest {

    @Test
    public void testGetReportCreekFound() {
        Map map = new Map();
        map.creeks.add(new POI(new MapTile("GROUND"), new MapCoordinate(0, 0), "be398c6e-9471-4949-991d-9be788d8616e"));
        FinalReport finalReport = new FinalReport(map);
        assertEquals("be398c6e-9471-4949-991d-9be788d8616e", finalReport.getReport());
    }

    @Test
    public void testGetReportCreekNotFound() {
        Map map = new Map();
        FinalReport finalReport = new FinalReport(map);
        assertEquals("No creek found", finalReport.getReport());
    }
}

