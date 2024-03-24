package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapCoordinateTest {

    private MapCoordinate mapCoordinate;
    @BeforeEach
    public void setUp() {
        this.mapCoordinate = new MapCoordinate(15, 15);
    }

    @Test
    public void testGetX() {
        assertEquals(15, mapCoordinate.getX());
    }

    @Test
    public void testGetY() {
        assertEquals(15, mapCoordinate.getY());
    }

    @Test
    public void testToString() {
        assertEquals("(15, 15)", mapCoordinate.toString());
    }

    @Test
    public void testTranslateFwd() {
        mapCoordinate.translateFwd(Direction.NORTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.translateFwd(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.translateFwd(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.translateFwd(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }

    @Test
    public void testTranslateLeft() {
        mapCoordinate.translateLeft(Direction.NORTH);
        assertEquals(16, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.translateLeft(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.translateLeft(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.translateLeft(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }

    @Test
    public void testTranslateRight() {
        mapCoordinate.translateRight(Direction.NORTH);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.translateRight(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.translateRight(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(16, mapCoordinate.getY());

        mapCoordinate.translateRight(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }
}