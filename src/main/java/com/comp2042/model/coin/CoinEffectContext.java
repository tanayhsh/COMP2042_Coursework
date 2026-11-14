package com.comp2042.model.coin;

import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public interface CoinEffectContext {
    Snake getSnake();
    Obstacle getObstacle();
    Game getGame();
    void setSpeedBonus(double bonus);
}
