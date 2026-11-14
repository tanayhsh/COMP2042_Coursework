package com.comp2042.model.coin;

import com.comp2042.model.CoinType;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

import java.awt.Point;
import java.util.Random;

public abstract class Coin {

    private static final Random random = new Random();
    private final Point pos;

    protected Coin(int width, int height, Snake snake, Obstacle obstacle) {
        Point p;
        do {
            p = new Point(random.nextInt(width), random.nextInt(height));
        } while (snake.getHead().equals(p)
                || snake.getBody().contains(p)
                || obstacle.getObstacles().contains(p));
        pos = p;
    }

    public Point getPos() {
        return pos;
    }

    public abstract int getPoints();

    public abstract CoinType getType();

    public void applyEffect(CoinEffectContext context){
    }
}