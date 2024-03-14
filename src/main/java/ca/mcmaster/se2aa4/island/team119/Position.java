package ca.mcmaster.se2aa4.island.team119;

public class Position {

    private int[] pos;

    public Position(int i, int i1) {
        this.pos = new int[] {i, i1};
    }

    public int getPos(int i) {
        return (pos[i]);
    }
}
