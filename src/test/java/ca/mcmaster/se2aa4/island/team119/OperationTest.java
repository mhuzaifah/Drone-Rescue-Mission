package ca.mcmaster.se2aa4.island.team119;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void isEchoLeft() {
        Operation operation = new Operation(Action.ECHOLEFT);
        assertTrue(operation.isEchoLeft());
        assertFalse(operation.isEchoRight());
        assertFalse(operation.isEchoFwd());
        assertTrue(operation.isEcho());
        assertFalse(operation.isFly());
        assertFalse(operation.isScan());
        assertFalse(operation.isFlyFwd());
    }

    @Test
    void isEchoRight() {
        Operation operation = new Operation(Action.ECHORIGHT);
        assertTrue(operation.isEchoRight());
        assertFalse(operation.isEchoLeft());
        assertFalse(operation.isEchoFwd());
        assertTrue(operation.isEcho());
        assertFalse(operation.isFly());
        assertFalse(operation.isScan());
        assertFalse(operation.isFlyFwd());
    }

    @Test
    void isEchoFwd() {
        Operation operation = new Operation(Action.ECHOFORWARD);
        assertTrue(operation.isEchoFwd());
        assertFalse(operation.isEchoLeft());
        assertFalse(operation.isEchoRight());
        assertTrue(operation.isEcho());
        assertFalse(operation.isFly());
        assertFalse(operation.isScan());
        assertFalse(operation.isFlyFwd());
    }

    @Test
    void isEcho() {
        Operation echoLeft = new Operation(Action.ECHOLEFT);
        Operation echoRight = new Operation(Action.ECHORIGHT);
        Operation echoFwd = new Operation(Action.ECHOFORWARD);

        assertTrue(echoLeft.isEcho());
        assertTrue(echoRight.isEcho());
        assertTrue(echoFwd.isEcho());

        assertFalse(new Operation(Action.SCAN).isEcho());
        assertFalse(new Operation(Action.FLYFORWARD).isEcho());
        assertFalse(new Operation(Action.FLYLEFT).isEcho());
        assertFalse(new Operation(Action.FLYRIGHT).isEcho());
    }

    @Test
    void isFly() {
        Operation flyLeft = new Operation(Action.FLYLEFT);
        Operation flyRight = new Operation(Action.FLYRIGHT);
        Operation flyFwd = new Operation(Action.FLYFORWARD);

        assertTrue(flyLeft.isFly());
        assertTrue(flyRight.isFly());
        assertTrue(flyFwd.isFly());

        assertFalse(new Operation(Action.SCAN).isFly());
        assertFalse(new Operation(Action.ECHOFORWARD).isFly());
        assertFalse(new Operation(Action.ECHORIGHT).isFly());
        assertFalse(new Operation(Action.ECHOLEFT).isFly());
    }

    @Test
    void isScan() {
        Operation operation = new Operation(Action.SCAN);
        assertTrue(operation.isScan());
        assertFalse(operation.isEchoLeft());
        assertFalse(operation.isEchoRight());
        assertFalse(operation.isEchoFwd());
        assertFalse(operation.isEcho());
        assertFalse(operation.isFly());
        assertFalse(operation.isFlyFwd());
    }

    @Test
    void isFlyFwd() {
        Operation operation = new Operation(Action.FLYFORWARD);
        assertTrue(operation.isFlyFwd());
        assertFalse(operation.isEchoLeft());
        assertFalse(operation.isEchoRight());
        assertFalse(operation.isEchoFwd());
        assertFalse(operation.isEcho());
        assertTrue(operation.isFly());
        assertFalse(operation.isScan());
    }

    @Test
    void getAction() {
        Operation operation = new Operation(Action.SCAN);
        assertEquals(Action.SCAN, operation.getAction());
    }
}
