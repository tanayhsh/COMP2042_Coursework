package com.comp2042.model;

import com.comp2042.model.coin.Coin;
import com.comp2042.model.coin.RedCoin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;


class ObstacleTest {

    private static final int size = 20;

    private Obstacle spawn(int amount, Snake[] snakeOut){
        Obstacle obstacle = new Obstacle(size, size);
        Snake snake = new Snake(size, size, false, obstacle);
        Coin coin = new RedCoin(size, size, snake,obstacle);
        obstacle.newObstacles(snake, coin, amount);
        if (snakeOut != null) snakeOut[0] = snake;
        return obstacle;
    }

    @Test
    void obstacleSpawnInsideCard(){
        for (Point p: spawn(40, null).getObstacles()){
            assertTrue(p.x >= 0 && p.y <= size, "x out of range " + p);
            assertTrue(p.y >= 0 && p.x <= size, "y out of range " + p);
        }
    }

    @Test
    void obstacleNeverStack(){
        List<Point> obstacles = spawn(40,null).getObstacles();
        assertEquals(obstacles.size(), new HashSet<>(obstacles).size());
    }

    @Test
    void obstaclesNotSpawnInSafeZone(){
        Snake[] snake = new Snake[1];
        Obstacle obstacle = spawn(40, snake);
        Point head = snake[0].getHead();

        for (Point p : obstacle.getObstacles()){
            int distance = Math.abs(p.x - head.x) + Math.abs(p.y - head.y);
            assertTrue(distance > 3, "obstacle too close to head " + p);
        }
    }

    @Test
    void resetClearAllObstacles(){
        Obstacle obstacle = spawn(40, null);
        obstacle.reset();
        assertTrue(obstacle.getObstacles().isEmpty());
    }
}

