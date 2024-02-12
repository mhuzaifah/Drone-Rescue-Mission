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

    @Override
    public String toString() {
        return this.abreviation;
    }

    public Direction lookRight() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction lookLeft() {
        switch (this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case EAST: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}

