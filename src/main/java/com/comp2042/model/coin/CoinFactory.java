package com.comp2042.model.coin;

import com.comp2042.model.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class CoinFactory {

    private final Random random;
    private final Map<CoinType, Integer> weight = new EnumMap<>(CoinType.class);

    public CoinFactory(Difficulty difficulty){
        this(difficulty, new Random());
    }

    public CoinFactory(Difficulty difficulty, Random random){
        this.random = random;
        weight.put(CoinType.RED, 80);
        weight.put(CoinType.GOLD, 6);
        weight.put(CoinType.BLUE, difficulty.isNoBorder() ? 0 : 5);
        weight.put(CoinType.RAINBOW, 5);
        weight.put(CoinType.SILVER, 4);
    }

    public Coin create(int width, int height, Snake snake, Obstacle obstacle, boolean powerUps){
        CoinType type = powerUps
                ? pickType(!obstacle.getObstacles().isEmpty())
                : CoinType.RED;

        return switch (type){
            case GOLD -> new GoldCoin(width, height, snake, obstacle);
            case BLUE -> new BlueCoin(width, height, snake, obstacle);
            case RAINBOW-> new RainbowCoin(width, height, snake, obstacle);
            case SILVER -> new SilverCoin(width, height, snake, obstacle);
            default -> new RedCoin(width, height, snake, obstacle);
        };
    }

    CoinType pickType(boolean obstacleExists){
        int total = 0;
        for(CoinType type: weight.keySet()){
            if(isEligible(type, obstacleExists)){
                total += weight.get(type);
            }
        }

        int roll = random.nextInt(total);
        for (CoinType type: weight.keySet()){
            if(!isEligible(type, obstacleExists)) continue;
            roll -= weight.get(type);
            if (roll < 0){
                return type;
            }
        }
        return CoinType.RED;
    }

    private boolean isEligible(CoinType type, boolean obstacleExists){
        if (weight.get(type) == 0) return false;
        return !(type == CoinType.SILVER && obstacleExists);
    }
}
