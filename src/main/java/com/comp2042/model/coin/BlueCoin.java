package com.comp2042.model.coin;

import com.comp2042.model.CoinType;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public class BlueCoin extends Coin{

    public BlueCoin(int width, int height, Snake snake, Obstacle obstacle){
        super(width, height, snake, obstacle);
    }

    @Override
    public int getPoints(){
        return 5;
    }

    @Override
    public CoinType getType() {
        return CoinType.BLUE;
    }
}

