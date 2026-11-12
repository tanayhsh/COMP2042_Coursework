package com.comp2042.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest{

    private Game newGame(){
        return new Game(20,20,true, 'E');
    }

    private Game newGame(int width, int height){
        return new Game(width, height, true, 'E');
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

    @Test
    void notWonBeforeBoardIsFull(){
        Game game = newGame(2, 2);
        game.incScore();
        assertFalse(game.gameWon());
    }

    @Test
    void wonWhenScoreReachesCoinsToWin(){
        Game game = newGame(2,2);
        game.incScore();
        game.incScore();
        assertTrue(game.gameWon());
    }

    @Test
    void obstaclesReduceCoinsNeededToWin(){
        Game game = newGame(3,3);
        assertEquals(7, game.getMaxScore());
        game.decMaxScore(2);
        assertEquals(5, game.getMaxScore());
        for (int i =0; i < 5; i++){
            game.incScore();
        }assertTrue(game.gameWon());

    }

}
