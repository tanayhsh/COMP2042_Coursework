package com.comp2042;

public class GoldCoin extends Coin {

    public GoldCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle);
    }

    public int getPoints() {
        int points = 15;
        return points;
    }

    @Override
    public CoinType getType(){
        return CoinType.GOLD;
    }
}
