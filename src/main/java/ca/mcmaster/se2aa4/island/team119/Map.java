package ca.mcmaster.se2aa4.island.team119;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {

    private Position currentPos;
    private Drone drone;

    Map(Drone drone) {
        this.drone = drone;
        this.currentPos = new Position(0,0);
    }

    ArrayList<Position> prevPos;

    int[][] creeks;

    int[] emergencySites;

    public boolean isExplored(int[] pos){
        for (Position prevPo : prevPos) {
            if ((prevPo.getPos(0) == pos[0]) & (prevPo.getPos(1) == pos[1])) {
                return true;
            }
        }
        return false;
    }

    public void setCurrentPos(Position newCurrentPos) {
        prevPos.add(currentPos);
        currentPos = newCurrentPos;
    }

    public void findCreek() {}

    public void turnRight() {
        Position pos;
        switch(drone.direction){
            case EAST ->{
                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)+1);
            }
            case WEST -> {
                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)-1);
            }
            case NORTH -> {
                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)-1);
            }
            case SOUTH -> {
                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)+1);
            }
            default -> {
                pos = null;
            }
        }
        this.setCurrentPos(pos);
    }

    public void turnLeft() {
        Position pos;
        switch(drone.direction){
            case EAST ->{
                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)-1);
            }
            case WEST -> {
                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)+1);
            }
            case NORTH -> {
                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1)-1);
            }
            case SOUTH -> {
                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1)+1);
            }
            default -> {
                pos = null;
            }
        }
        this.setCurrentPos(pos);
    }

    public void forward() {
        Position pos;
        switch(drone.direction){
            case EAST ->{
                pos = new Position(currentPos.getPos(0)+1, currentPos.getPos(1));
            }
            case WEST -> {
                pos = new Position(currentPos.getPos(0)-1, currentPos.getPos(1));
            }
            case NORTH -> {
                pos = new Position(currentPos.getPos(0), currentPos.getPos(1)-1);
            }
            case SOUTH -> {
                pos = new Position(currentPos.getPos(0), currentPos.getPos(1)+1);
            }
            default -> {
                pos = null;
            }
        }
        this.setCurrentPos(pos);
    }
}
