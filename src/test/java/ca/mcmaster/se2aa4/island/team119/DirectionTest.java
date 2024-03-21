package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DirectionTest {

    @Test
    public void testToDirection() {
        assertEquals(Direction.NORTH, Direction.toDirection("N"));
        assertEquals(Direction.SOUTH, Direction.toDirection("S"));
        assertEquals(Direction.EAST, Direction.toDirection("E"));
        assertEquals(Direction.WEST, Direction.toDirection("W"));
    }

    //If the Direction.toDirection("X") method indeed throws an IllegalArgumentException,
    //the test passes. If it doesn't throw an exception or throws a different type of exception, the test fails.
    @Test
    public void testToDirectionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Direction.toDirection("X");
        });
    }

    @Test
    public void testLookRight() {
        assertEquals(Direction.EAST, Direction.NORTH.lookRight());
        assertEquals(Direction.SOUTH, Direction.EAST.lookRight());
        assertEquals(Direction.WEST, Direction.SOUTH.lookRight());
        assertEquals(Direction.NORTH, Direction.WEST.lookRight());
    }

    @Test
    public void testLookLeft() {
        assertEquals(Direction.WEST, Direction.NORTH.lookLeft());
        assertEquals(Direction.SOUTH, Direction.WEST.lookLeft());
        assertEquals(Direction.EAST, Direction.SOUTH.lookLeft());
        assertEquals(Direction.NORTH, Direction.EAST.lookLeft());
    }
}
