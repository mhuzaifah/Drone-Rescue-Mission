package ca.mcmaster.se2aa4.island.team119;

import java.util.Arrays;

public class Map {

    Map() {}

    int[] currentPos;

    int[][] prevPos;

    int[][] creeks;

    int[] emergencySites;

    public boolean isExplored(int[] pos){
        for (int[] prevPo : prevPos) {
            if (Arrays.equals(prevPo, pos)) {
                return true;
            }
        }
        return false;
    }

    public void findCreek() {}

}
