package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapCoordinateTest {

    private MapCoordinate mapCoordinate;
    @BeforeEach
    public void setUp() {
        mapCoordinate = new MapCoordinate(15, 15);
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
    public void testFlyFwd() {
        mapCoordinate.flyFwd(Direction.NORTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.flyFwd(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.flyFwd(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.flyFwd(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }

    @Test
    public void testFlyLeft() {
        mapCoordinate.flyLeft(Direction.NORTH);
        assertEquals(16, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.flyLeft(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.flyLeft(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.flyLeft(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }

    @Test
    public void testFlyRight() {
        mapCoordinate.flyRight(Direction.NORTH);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(14, mapCoordinate.getY());

        mapCoordinate.flyRight(Direction.SOUTH);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());

        mapCoordinate.flyRight(Direction.EAST);
        assertEquals(14, mapCoordinate.getX());
        assertEquals(16, mapCoordinate.getY());

        mapCoordinate.flyRight(Direction.WEST);
        assertEquals(15, mapCoordinate.getX());
        assertEquals(15, mapCoordinate.getY());
    }
}