package com.comp2042.model.coin;

import com.comp2042.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

class CoinFactoryTest {

    private static final int size = 20;
    private static final int draws = 10000;

    private Map<CoinType, Integer> countPicks(CoinFactory factory,boolean obstaclesExists){
        Map<CoinType, Integer> counts = new EnumMap<>(CoinType.class);
        for (int i = 0; i < draws; i++){
            counts.merge(factory.pickType(obstaclesExists), 1, Integer::sum);
        }
        return counts;
    }

    @Test
    void redCoinsSpawnAbout80Percent(){
        CoinFactory factory = new CoinFactory(Difficulty.NORMAL, new Random(42));
        double redPercent = countPicks(factory, false).get(CoinType.RED) / (double) draws;
        assertTrue(redPercent > 0.77 && redPercent < 0.83, "red percent was " + redPercent);
    }

    @Test
    void powerUpsSpawnOnNormal(){
        CoinFactory factory = new CoinFactory(Difficulty.NORMAL, new Random(42));
        Map<CoinType, Integer> counts = countPicks(factory, false);
        for (CoinType type : CoinType.values()){
            assertTrue(counts.getOrDefault(type, 0) > 0, type + " never spawned");
        }
    }

    @Test
    void silverDoesntSpawnWhenObstacleExist(){
        CoinFactory factory = new CoinFactory(Difficulty.NORMAL, new Random(42));
        assertEquals(0, countPicks(factory, true).getOrDefault(CoinType.SILVER, 0));
    }

    @Test
    void blueDoesntSpawnOnEasy(){
        CoinFactory factory = new CoinFactory(Difficulty.EASY, new Random(42));
        assertEquals(0, countPicks(factory, false).getOrDefault(CoinType.BLUE, 0));
    }

    @Test
    void onlyRedSpawnsWhenPowerUpsOff(){
        Obstacle obstacle = new Obstacle(size, size);
        Snake snake = new Snake(size, size, false, obstacle);
        CoinFactory factory = new CoinFactory(Difficulty.NORMAL, new Random(42));
        for (int i = 0; i < 200; i++){
            assertEquals(CoinType.RED, factory.create(size, size,snake, obstacle, false).getType());
        }
    }
}
