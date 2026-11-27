package com.comp2042;

import com.comp2042.model.HighScore;
import com.comp2042.savedata.HighScoreFile;
import com.comp2042.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static ViewManager viewManager;
    private static final HighScoreFile highScoreFile = HighScoreFile.inUserHome();
    private static final HighScore highScore = highScoreFile.load();

    @Override
    public void start(Stage primaryStage) {
        viewManager = new ViewManager(primaryStage);
        viewManager.initializeMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ViewManager getViewManager() {
        return viewManager;
    }

    public static HighScore getHighScore(){
        return highScore;
    }

    public static void saveHighScore(){
        highScoreFile.save(highScore);
    }
}