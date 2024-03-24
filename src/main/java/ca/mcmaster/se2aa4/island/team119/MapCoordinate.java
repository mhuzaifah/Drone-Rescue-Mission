// Muhammad Huzaifa, Anam Khan, Haniya Kashif
// date: 24/03/2024
// TA: Eshaan Chaudhari
// MapCoordinate
// makes mapCoordinates that Map is composed of, and updates coordinates after a move is made

package ca.mcmaster.se2aa4.island.team119;

public class MapCoordinate {
    private int x;
    private int y;

    MapCoordinate(Integer xCor, Integer yCor){
        this.x = xCor;
        this.y = yCor;
    }

    // returns x coordinate
    public int getX() {
        return x;
    }

    // returns y coordinate
    public int getY() {
        return y;
    }

    // returns the coordinates as a string
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // changes the current x and y coordinate after the drone flys forward
    public void translateFwd(Direction droneHeading) {
        switch(droneHeading){
            case NORTH -> y--;
            case SOUTH -> y++;
            case EAST -> x--;
            case WEST -> x++;
        }
    }

    // changes the current x and y coordinate after the drone flys left
    public void translateLeft(Direction droneHeading) {
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

    // changes the current x and y coordinate after the drone flys right
    public void translateRight(Direction droneHeading) {
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

    // calculates distance between 2 coordinates
    public double calculateDistance (MapCoordinate coord1, MapCoordinate coord2) {
        return Math.sqrt(Math.pow((coord1.getX() - coord2.getX()), 2) + Math.pow((coord1.getY() - coord2.getY()), 2));
    }
}