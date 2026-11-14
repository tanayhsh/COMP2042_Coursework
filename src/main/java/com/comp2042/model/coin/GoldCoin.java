package com.comp2042.model.coin;

import com.comp2042.model.CoinType;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public class GoldCoin extends Coin{

    public GoldCoin(int width, int height, Snake snake, Obstacle obstacle){
        super(width, height, snake, obstacle);
    }

    @Override
    public int getPoints(){
        return 15;
    }

    @Override
    public CoinType getType() {
        return CoinType.GOLD;
    }
}

