package com.comp2042.model.coin;

import com.comp2042.model.CoinType;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import com.comp2042.model.Game;

import java.util.Random;

public class SilverCoin extends Coin{

    private static final Random random = new Random();
    public SilverCoin(int width, int height, Snake snake, Obstacle obstacle){
        super(width, height, snake, obstacle);
    }

    @Override
    public int getPoints(){
        return 5;
    }

    @Override
    public CoinType getType() {
        return CoinType.SILVER;
    }

    @Override
    public void applyEffect(CoinEffectContext context){
        Game game = context.getGame();
        int amount = random.nextInt(game.getWidth() * game.getHeight() / 20) + 3;
        int added = context.getObstacle().newObstacles(context.getSnake(), this, amount);
        game.decMaxScore(added);
    }
}


