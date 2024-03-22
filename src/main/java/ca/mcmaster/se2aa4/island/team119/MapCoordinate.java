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

    public void flyFwd(Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> y--;
            case SOUTH -> y++;
            case EAST -> x--;
            case WEST -> x++;
        }
    }


    public void flyLeft(Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> {
                x++;
                y--;
            }
            case SOUTH -> {
                x--;
                y++;
            }
            case EAST -> {
                x--;
                y--;
            }
            case WEST -> {
                x++;
                y++;
            }
        }
    }

    public void flyRight(Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> {
                x--;
                y--;
            }
            case SOUTH -> {
                x++;
                y++;
            }
            case EAST -> {
                x--;
                y++;
            }
            case WEST -> {
                x++;
                y--;
            }
        }
    }
}