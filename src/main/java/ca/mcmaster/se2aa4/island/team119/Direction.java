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
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Direction lookLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

}

