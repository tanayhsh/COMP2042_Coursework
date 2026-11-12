package com.comp2042.model;

import com.comp2042.model.coin.Coin;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle {
    private static final int safe_dist = 3;
    private Color color = Color.BLACK;
    private ArrayList<Point> obstacles = new ArrayList<>();
    private int x;
    private int y;

    public Obstacle(int x, int y) {
       this.x = x;
       this.y = y;
    }

    public void newObstacles(Snake snake, Coin coin, int amount) {
        if (obstacles.size() > 50) {
            return;
        }
        Random rnd = new Random();
        Point p;
        for (int i=0; i<amount; i++) {
            do {
                p = new Point(rnd.nextInt(x), rnd.nextInt(y));                      // removed + 1 to match the grid
            } while (coin.getPos().equals(p)
                    || snake.getBody().contains(p)
                    || snake.getHead().equals(p)
                    || obstacles.contains(p)   // added obstacles.contains(p) to ensure obstacles dont spawn on each other
                    || tooCLoseToHead(p, snake.getHead()));
            obstacles.add(p);
        }
    }

    private boolean tooCLoseToHead(Point p, Point head){
        return Math.abs(p.x - head.x) + Math.abs(p.y - head.y) <= safe_dist;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Point> getObstacles() {
        return obstacles;
    }

    public void reset() {
        obstacles.clear();
    }
}
