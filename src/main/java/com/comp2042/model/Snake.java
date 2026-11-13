package com.comp2042.model;

import com.comp2042.model.coin.Coin;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private boolean dead = false;
    private final Point head = new Point();
    private final ArrayList<Point> body = new ArrayList<>();
    private final int gx;
    private final int gy;
    private boolean ignoreBorders = false;
    private boolean noBorder;
    private Obstacle obstacle;
    private int snakeDir = 270;

    public Snake(int x, int y, boolean noBorder, Obstacle obstacle) {
        this.obstacle = obstacle;
        this.noBorder = noBorder;
        this.head.setLocation(x / 2, y / 2);
        this.body.add(new Point(x / 2 + 1, y / 2));
        this.gx = x;
        this.gy = y;
    }

    /**
     * Moves the snake one cell in the given direction, wrapping or dying
     * at the border, and dying on obstacles or its own body.
     * @param dir the direction to move
     */
    public void move(Direction dir){
        Point previous = new Point(head);
        body.add(new Point(head));
        snakeDir = dir.getAngle();
        head.translate(dir.getDx(), dir.getDy());

        if (isOutOfBounds()){
            if (ignoreBorders || noBorder){
                wrap();
            } else {
                head.setLocation(previous);
                dead = true;
            }
        }

        // checked after any wrapped so the snake can never land on an obstacle
        if (!dead && obstacle.getObstacles().contains(head)){
            head.setLocation(previous);
            dead = true;
        }

        // if the head moved back because of death, undo the segment
        // added at the start of this move so there's no overlap
        if (dead && head.equals(previous)){
            body.remove(body.size() - 1);

        }

        if (body.size() > 4 && hitSelf() && !body.get(0).equals(head)) {
            dead = true;
        }
    }



    public int getBodyDir(int bodyCount) {
        if (bodyCount == 0) {
            return 0;
        } else if (body.get(bodyCount).getY() == body.get(bodyCount - 1).getY()) {
            return 270;
        } else {
            return 0;
        }
    }

    public boolean ateCoin(Coin coin) {
        if (!head.equals(coin.getPos())) {
            body.remove(0);
        }
        return head.equals(coin.getPos());
    }

    private boolean isOutOfBounds() {
        return head.getX() < 0 || head.getY() < 0 || head.getX() >= gx || head.getY() >= gy;
    }

    /** Moves the head to the opposite side of the board */
    private void wrap(){
        head.setLocation(Math.floorMod(head.x, gx), Math.floorMod(head.y, gy));
    }

    public boolean hitSelf() {
        return body.contains(head);
    }

    public ArrayList<Point> getBody() {
        return new ArrayList<>(body);
    }

    public Point getHead() {
        return new Point(head);
    }

    public boolean isDead() {
        return dead;
    }

    public void ignoreBorders(boolean ign) {
        ignoreBorders = ign;
    }

    public int getSnakeDir() {
        return snakeDir;
    }
}
