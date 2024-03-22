package ca.mcmaster.se2aa4.island.team119;

public class MapCoordinate {
    private int x;
    private int y;

    public MapCoordinate() {}

    /*MapCoordinate(Integer xCor, Integer yCor){
        this.x = xCor;
        this.y = yCor;
    }*/

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void droneTranslate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MapCoordinate other = (MapCoordinate) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}