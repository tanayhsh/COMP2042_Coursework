package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;


class SnakeTest {
    private static final int size = 20;
    private Obstacle obstacle;

    @BeforeEach
    void setup(){
        obstacle = new Obstacle(size,size);
    }

    @Test
    void startInCentreWithOneBodySize(){
        Snake snake = new Snake(size, size, false, obstacle);
        assertEquals(new Point(10,10), snake.getHead());
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(11,10), snake.getBody().get(0));
        assertFalse(snake.isDead());
    }

    @Test
    void movingLeftMovesHeadOneSpace(){
        Snake snake = new Snake(size, size, false, obstacle);
        assertEquals(new Point(10,10), snake.getHead());
        snake.move(Direction.LEFT);
        assertEquals(new Point(11,10), snake.getBody().get(0));
        assertFalse(snake.isDead());
    }

    @Test
    void hittingWallWhenBorderOnKillSnake(){
        Snake snake = new Snake(size, size, false, obstacle);
        for (int i = 0; i < 11; i++){
            snake.move(Direction.UP);
        }
        assertTrue(snake.isDead());
        assertEquals(new Point(10,0), snake.getHead());
    }

    @Test
    void wrapsToOtherEdgeWhenNoBorder(){
        Snake snake = new Snake(size, size, true, obstacle);
        for (int i = 0; i < 11; i++){
            snake.move(Direction.UP);
        }
        assertFalse(snake.isDead());
        assertEquals(new Point(10,19), snake.getHead());
    }

    @Test
    void hittingObstacleKillSnake(){
        Snake snake = new Snake(size, size, false, obstacle);
        obstacle.getObstacles().add(new Point(9,10));
        snake.move(Direction.LEFT);
        assertTrue(snake.isDead());

    }

    @Test
    void wrapOntoObstacleKillSnake(){
        Snake snake = new Snake(size, size, true, obstacle);
        obstacle.getObstacles().add(new Point(10,19));
        for (int i = 0; i < 11; i++){
            snake.move(Direction.UP);
        }
        assertTrue(snake.isDead());
        assertEquals(new Point(10,0), snake.getHead());
    }

    @Test
    void dyingDoesNotLeaveBodySegmentUnderHead() {
        obstacle.getObstacles().add(new Point(9, 10));
        Snake snake = new Snake(size, size, false, obstacle);
        snake.move(Direction.LEFT);
        assertTrue(snake.isDead());
        assertFalse(snake.getBody().contains(snake.getHead()));
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void wrapsCorrectlyInALLDirections(){
        Snake snake = new Snake(size, size, true, obstacle);
        for(int i = 0; i < 11; i++){
            snake.move(Direction.LEFT);
        }
        assertEquals(19, snake.getHead().x);
        assertFalse(snake.isDead());
    }
}
