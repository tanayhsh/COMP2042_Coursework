package com.comp2042.model.coin;

import com.comp2042.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class CoinEffectTest {
    private static final int size = 20;
    private Snake snake;
    private Obstacle obstacle;
    private Game game;
    private FakeContext context;

    private class FakeContext implements CoinEffectContext {
        double speedBonus = 0;

        public Snake getSnake() {
            return snake;
        }

        public Game getGame() {
            return game;
        }

        public Obstacle getObstacle() {
            return obstacle;
        }

        public void setSpeedBonus(double bonus) {
            speedBonus = bonus;
        }
    }

    @BeforeEach
    void setUp(){
        obstacle = new Obstacle(size, size);
        snake = new Snake(size, size, false, obstacle);
        game = new Game(size, size , true, Difficulty.NORMAL);
        context = new FakeContext();
    }

    @Test
    void redAndGoldHaveNoEffect(){
        new RedCoin(size, size, snake, obstacle).applyEffect(context);
        new GoldCoin(size, size, snake, obstacle).applyEffect(context);
        assertEquals(0, context.speedBonus);
        assertTrue(obstacle.getObstacles().isEmpty());
    }

    @Test
    void rainbowCoinSpeedBonus(){
        new RainbowCoin(size, size, snake, obstacle).applyEffect(context);
        assertEquals(5.0, context.speedBonus);
    }

    @Test
    void blueCoinAllowsSnakeToWrapThroughWalls(){
        new BlueCoin(size, size, snake, obstacle).applyEffect(context);
        for (int i = 0; i < 11; i ++){
            snake.move(Direction.UP);
        }
        assertFalse(snake.isDead());
        assertEquals(19, snake.getHead().y);
    }

    @Test
    void silverCoinSpawnsObstaclesAndReducesCoinsToWin(){
        int before = game.getMaxScore();
        new SilverCoin(size, size, snake, obstacle).applyEffect(context);
        int added = obstacle.getObstacles().size();
        assertTrue(added >= 3);
        assertEquals(before - added, game.getMaxScore());
    }
}
