package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoreTest {

    private HighScore score;

    @BeforeEach
    void setup(){
        score = new HighScore();
    }

    @Test
    void everyDifficultyStartsAtZero(){
        for (Difficulty difficulty: Difficulty.values()){
            assertEquals(0, score.getBest(difficulty));
        }
    }

    @Test
    void higherScoreChangesBestScoreValue(){
        assertTrue(score.submit(Difficulty.NORMAL, 40));
        assertEquals(40, score.getBest(Difficulty.NORMAL));
    }

    @Test
    void lowerOrEqualDoesntChangeBestScoreValue(){
        score.submit(Difficulty.NORMAL, 40);
        assertFalse(score.submit(Difficulty.NORMAL, 25));
        assertFalse(score.submit(Difficulty.NORMAL, 40));
        assertEquals(40, score.getBest(Difficulty.NORMAL));
    }

    @Test
    void scoringZeroHasNoEffectOnBestScore(){
        assertFalse(score.submit(Difficulty.EASY, 0));
    }

    @Test
    void differentDifficultyGotDifferentBestScores(){
        score.submit(Difficulty.EASY, 100);
        score.submit(Difficulty.NORMAL, 50);
        score.submit(Difficulty.HARD, 30);
        assertEquals(100, score.getBest(Difficulty.EASY));
        assertEquals(50, score.getBest(Difficulty.NORMAL));
        assertEquals(30, score.getBest(Difficulty.HARD));
    }

    @Test
    void negativeValueIsTreatedAsZero(){
        score.setBest(Difficulty.NORMAL, -5);
        assertEquals(0, score.getBest(Difficulty.NORMAL));
    }
}
