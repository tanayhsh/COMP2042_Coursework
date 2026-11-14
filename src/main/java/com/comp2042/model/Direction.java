package com.comp2042.model;

public enum Direction {
    UP(0, -1, 0),
    DOWN(0, 1, 180),
    LEFT(-1, 0, 270),
    RIGHT(1, 0, 90);

    private final int dx;
    private final int dy;
    private final int angle;

    Direction(int dx, int dy, int angle){
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
    }

    /** @return horizontal change per move (-1, 0 or 1) */
    public int getDx() {
        return dx;
    }

    /** @return vertical change per move (-1, 0 or 1) */
    public int getDy() {
        return dy;
    }

    /** @return rotation in degrees used to draw the head */
    public int getAngle() {
        return angle;
    }

    /**
     * @param other another direction
     * @return true if two directions point opposite way
     */
    public boolean isOpposite(Direction other){
        return dx == -other.dx && dy == -other.dy;
    }
}
