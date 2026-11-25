package com.comp2042.model;

import java.util.EnumMap;
import java.util.Map;

public class HighScore {

    private final Map<Difficulty, Integer> best = new EnumMap<>(Difficulty.class);

    public HighScore(){
        for (Difficulty difficulty: Difficulty.values()){
            best.put(difficulty, 0);
        }
    }

    public int getBest(Difficulty difficulty){
        return  best.get(difficulty);
    }

    public boolean submit(Difficulty difficulty, int score){
        if (score > getBest(difficulty)){
            best.put(difficulty, score);
            return true;
        }
        return false;
    }

    public void setBest(Difficulty difficulty, int score){
        best.put(difficulty, Math.max(0, score));
    }
}
