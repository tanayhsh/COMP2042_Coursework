package com.comp2042.savedata;

import com.comp2042.model.Difficulty;
import com.comp2042.model.HighScore;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class HighScoreFile {

    private final Path path;

    public HighScoreFile(Path path){
        this. path = path;
    }

    private static HighScoreFile inUserHome(){
        return new HighScoreFile(Path.of(System.getProperty("user.home"), ".snakegame", "highscores.properties"));
    }

    public HighScore load(){
        HighScore score = new HighScore();
        if (!Files.exists(path)){
            return score;
        }

        Properties saved = new Properties();
        try (Reader reader = Files.newBufferedReader(path)){
            saved.load(reader);
        } catch (IOException e){
            System.err.println("Could not read high scores from " + path + ": " + e.getMessage());
            return score;
        }

        for (Difficulty difficulty : Difficulty.values()){
            String value = saved.getProperty(difficulty.name());
            if (value == null) continue;
            try{
                score.setBest(difficulty, Integer.parseInt(value.trim()));
            } catch (NumberFormatException e){

            }
        }
        return score;
    }

    public void save(HighScore score){
        Properties toSave = new Properties();
        for (Difficulty difficulty : Difficulty.values()){
            toSave.setProperty(difficulty.name(), String.valueOf(score.getBest(difficulty)));
        }
        try{
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)){
                toSave.store(writer, "Snake Game High Score");
            }
        } catch (IOException e){
            System.err.println("Could not save high score to " + path + ": " + e.getMessage());
        }
    }
}


