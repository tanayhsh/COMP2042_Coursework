package com.comp2042.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GameTest{

    private Game newGame(Difficulty difficulty){
        return new Game(20,20,true, difficulty );
    }

    private Game newGame(int width, int height, Difficulty difficulty){
        return new Game(width, height, true, difficulty);
    }

    @Test
    void calculateMaxScoreCorrectly(){
        assertEquals(398, newGame(Difficulty.EASY).getMaxScore());
    }

    @Test
    void testObstacleDecreasesMaxScore(){
        Game game = newGame(Difficulty.EASY);
        game.decMaxScore(5);
        assertEquals(393, game.getMaxScore());
    }

    @Test
    void resetRestoresMaxScore(){
        Game game = newGame(Difficulty.EASY);
        game.decMaxScore(5);
        game.resetScore();
        assertEquals(398, game.getMaxScore());
    }

    @Test
    void resetClearsMaxScoreAndPoints(){
        Game game = newGame(Difficulty.EASY);
        game.incScore();
        game.addPoints(15);
        game.resetScore();
        assertEquals(0, game.getScorePoints());
        assertFalse(game.gameWon());
    }

    @Test
    void notWonBeforeBoardIsFull(){
        Game game = newGame(2, 2, Difficulty.EASY);
        game.incScore();
        assertFalse(game.gameWon());
    }

    @Test
    void wonWhenScoreReachesCoinsToWin(){
        Game game = newGame(2,2, Difficulty.EASY);
        game.incScore();
        game.incScore();
        assertTrue(game.gameWon());
    }

    @Test
    void obstaclesReduceCoinsNeededToWin(){
        Game game = newGame(3,3, Difficulty.EASY);
        assertEquals(7, game.getMaxScore());
        game.decMaxScore(2);
        assertEquals(5, game.getMaxScore());
        for (int i =0; i < 5; i++){
            game.incScore();
        }
        assertTrue(game.gameWon());
    }

    @Test
    void difficultySetsBordersAndSpeed(){
        Game easy = newGame(Difficulty.EASY);
        Game hard = newGame(Difficulty.HARD);
        assertTrue(easy.isNoBorder());
        assertFalse(hard.isNoBorder());
        assertTrue(easy.getSpeed() < hard.getSpeed());
    }

}
