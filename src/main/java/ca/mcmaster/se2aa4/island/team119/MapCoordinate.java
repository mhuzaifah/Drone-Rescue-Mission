package ca.mcmaster.se2aa4.island.team119;

public class MapCoordinate {
    private int x;
    private int y;

    MapCoordinate(Integer xCor, Integer yCor){
        this.x = xCor;
        this.y = yCor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void flyFwd(Integer distance, Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> y -= distance;
            case SOUTH -> y += distance;
            case EAST -> x -= distance;
            case WEST -> x += distance;
            default -> throw new IllegalArgumentException("Invalid direction " + droneHeading);
        }
    }

    public void flyLeft(Integer distance, Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> {
                y -= distance;
                x += distance;
            }
            case EAST -> {
                x -= distance;
                y -= distance;
            }
            case SOUTH -> {
                y += distance;
                x -= distance;
            }
            case WEST -> {
                x += distance;
                y += distance;
            }
            default -> throw new IllegalStateException("Invalid direction");
        }
    }

    public void flyRight(Integer distance, Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> {
                y -= distance;
                x -= distance;
            }
            case EAST -> {
                x -= distance;
                y += distance;
            }
            case SOUTH -> {
                y += distance;
                x += distance;
            }
            case WEST -> {
                x += distance;
                y -= distance;
            }
            default -> throw new IllegalStateException("Invalid direction");

        }
    }
}