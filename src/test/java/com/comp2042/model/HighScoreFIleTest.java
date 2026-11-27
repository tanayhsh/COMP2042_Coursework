package com.comp2042.model;

import com.comp2042.savedata.HighScoreFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoreFIleTest {

    @TempDir
    Path tempDir;

    @Test
    void readAndWriteScoreProperly(){
        HighScoreFile file = new HighScoreFile(tempDir.resolve("scores.properties"));
        HighScore score = new HighScore();
        score.submit(Difficulty.EASY, 20);
        score.submit(Difficulty.HARD, 25);

        file.save(score);
        HighScore loaded = file.load();

        assertEquals(20, loaded.getBest(Difficulty.EASY));
        assertEquals(25, loaded.getBest(Difficulty.HARD));
        assertEquals(0, loaded.getBest(Difficulty.NORMAL));
    }

    @Test
    void startScoreAtZeroWhenFileMissing(){
        HighScore loaded = new HighScoreFile(tempDir.resolve("does-not-exist.properties")).load();
        for (Difficulty difficulty : Difficulty.values()){
            assertEquals(0, loaded.getBest(difficulty));
        }
    }

    @Test
    void negativeScoresAreSkipped() throws IOException {
        Path path = tempDir.resolve("score.properties");
        Files.writeString(path, "EASY=abc\nNORMAL=20\nHARD=-3\n");

        HighScore loaded = new HighScoreFile(path).load();

        assertEquals(0, loaded.getBest(Difficulty.EASY));
        assertEquals(20, loaded.getBest(Difficulty.NORMAL));
        assertEquals(0, loaded.getBest(Difficulty.HARD));
    }

    @Test
    void createMissingFolderWhenSaving(){
        Path path = tempDir.resolve("nested/folder/scores.properties");
        new HighScoreFile(path).save(new HighScore());
        assertTrue(Files.exists(path));
    }

}
