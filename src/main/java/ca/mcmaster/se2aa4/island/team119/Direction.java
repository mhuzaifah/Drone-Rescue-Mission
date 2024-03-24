// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// Direction
// enum of the directions
package ca.mcmaster.se2aa4.island.team119;

public enum Direction {

    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private String abreviation;

    Direction(String abv) {
        this.abreviation = abv;
    }

    // returns direction as a string of only the first letter
    @Override
    public String toString() {
        return this.abreviation;
    }

    // returns the direction as an element of the Direction enum
    // parameter - the string abreviation of the direction
    public static Direction toDirection(String direc) {
        if(direc.equals("N")) return NORTH;
        else if(direc.equals("S")) return SOUTH;
        else if(direc.equals("E")) return EAST;
        else if(direc.equals("W")) return WEST;
        else {
            throw new IllegalArgumentException("Invalid Direction " + direc);
        }
    }

    // returns the direction to the right of the current direction
    public Direction lookRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    // returns the direction to the left of the current direction
    public Direction lookLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    // these methods returns true if the direction is as specified, false if not
    public Boolean isNorth() {
        return this == Direction.NORTH;
    }
    public Boolean isSouth() {
        return this == Direction.SOUTH;
    }

    public Boolean isEast() {
        return this == Direction.EAST;
    }

    public Boolean isWest() {
        return this == Direction.WEST;
    }

}

