package com.comp2042.model.coin;

import com.comp2042.model.CoinType;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

import java.awt.Point;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinTest {
    private static final int size = 20;
    private final Obstacle obstacle = new Obstacle(size, size);
    private final Snake snake = new Snake(size, size, true, obstacle);

    @Test
    void coinHasCorrectPointsAndType(){
        assertCoin(new RedCoin(size, size, snake, obstacle), 5, CoinType.RED);
        assertCoin(new GoldCoin(size, size, snake, obstacle), 15, CoinType.GOLD);
        assertCoin(new BlueCoin(size, size, snake, obstacle), 5, CoinType.BLUE);
        assertCoin(new RainbowCoin(size, size, snake, obstacle), 5, CoinType.RAINBOW);
        assertCoin(new SilverCoin(size, size, snake, obstacle), 5, CoinType.SILVER);

    }

    @Test
    void coinNeverSpawnsOnSnakeObstacleOrOffGrid(){
        obstacle.getObstacles().add(new Point(0, 0));
        obstacle.getObstacles().add(new Point(5, 5));
        for (int i = 0; i < 1000; i++){
            Point p = new RedCoin(size, size, snake, obstacle).getPos();
            assertTrue(p.x >= 0 && p.y >= 0 && p.x <= size && p.y <= size, "off grid " + p);
            assertNotEquals(snake.getHead(), p);
            assertFalse(snake.getBody().contains(p));
            assertFalse(obstacle.getObstacles().contains(p));
        }
    }

    private void assertCoin(Coin coin, int points, CoinType type){
        assertEquals(points, coin.getPoints());
        assertEquals(type, coin.getType());
    }
}
