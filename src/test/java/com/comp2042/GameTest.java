package com.comp2042;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest{

    private Game newGame(){
        return new Game(20,20,true, 'E');
    }

    @Test
    void calculateMaxScoreCorrectly(){
        assertEquals(398, newGame().getMaxScore());
    }

    @Test
    void testObstacleDecreasesMaxScore(){
        Game game = newGame();
        game.decMaxScore(5);
        assertEquals(393, game.getMaxScore());
    }

    @Test
    void resetRestoresMaxScore(){
        Game game = newGame();
        game.decMaxScore(5);
        game.resetScore();
        assertEquals(398, game.getMaxScore());
    }

    @Test
    void resetClearsMaxScoreAndPoints(){
        Game game = newGame();
        game.incScore();
        game.addPoints(15);
        game.resetScore();
        assertEquals(0, game.getScorePoints());
        assertFalse(game.gameWon());
    }
}
