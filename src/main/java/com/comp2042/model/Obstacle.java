package com.comp2042.model;

import com.comp2042.model.coin.Coin;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle {
    private static final int safe_dist = 3;
    private static final int max_spawned = 50;
    private Color color = Color.BLACK;

    private final List<Point> walls = new ArrayList<>();        // for the map; permanent
    private final List<Point> spawned = new ArrayList<>();      // form silver coins; temporary
    private int x;
    private int y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int newObstacles(Snake snake, Coin coin, int amount) {
        if (spawned.size() > max_spawned) {
            return 0;
        }
        Random rnd = new Random();
        Point p;
        for (int i = 0; i < amount; i++) {
            do {
                p = new Point(rnd.nextInt(x), rnd.nextInt(y));                      // removed + 1 to match the grid
            } while (coin.getPos().equals(p) || snake.getBody().contains(p)
                    || snake.getHead().equals(p)
                    || isBlocked(p)                                         
                    || tooCLoseToHead(p, snake.getHead()));
            spawned.add(p);
        }
        return amount;
    }

    private boolean tooCLoseToHead(Point p, Point head) {
        return Math.abs(p.x - head.x) + Math.abs(p.y - head.y) <= safe_dist;
    }

    public Color getColor() {
        return color;
    }

    public List<Point> getObstacles() {
        List<Point> all = copyOf(walls);
        all.addAll(copyOf(spawned));
        return all;
    }

    public void reset() {
        spawned.clear();
    }

    public void setWalls(List<Point> newWalls) {
        walls.clear();
        for (Point wall : newWalls) {
            addWall(wall);
        }
    }

    public void addWall(Point wall) {
        walls.add(new Point(wall));
    }

    public boolean isBlocked(Point cell){
        return walls.contains(cell) || spawned.contains(cell);
    }

    public boolean hasSpawned(){
        return !spawned.isEmpty();
    }

    public List<Point> getWalls(){
        return copyOf(walls);
    }

    public List<Point> getSpawned(){
        return copyOf(spawned);
    }

    private static List<Point> copyOf(List<Point> points){
        List<Point> copy = new ArrayList<>();
        for (Point p : points){
            copy.add(new Point(p));
        }
        return copy;
    }
}
