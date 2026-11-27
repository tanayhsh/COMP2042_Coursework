package com.comp2042.controller;

import com.comp2042.Main;
import com.comp2042.model.Difficulty;
import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import com.comp2042.model.coin.Coin;
import com.comp2042.model.coin.RedCoin;
import com.comp2042.view.GameView;
import com.comp2042.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {

    private static final int X_VALUE = 20;
    private static final int Y_VALUE = 20;

    @FXML private ToggleButton btn_easy;
    @FXML private ToggleButton btn_normal;
    @FXML private ToggleButton btn_hard;
    @FXML private Label lbl_difficultyInfo;
    @FXML private CheckBox chk_powerUp;

    private final ToggleGroup difficultyGroup = new ToggleGroup();

    private Scene gameScene;

    private ViewManager viewManager = Main.getViewManager();

    @FXML
    private void initialize(){
        btn_easy.setUserData(Difficulty.EASY);
        btn_normal.setUserData(Difficulty.NORMAL);
        btn_hard.setUserData(Difficulty.HARD);

        btn_easy.setToggleGroup(difficultyGroup);
        btn_normal.setToggleGroup(difficultyGroup);
        btn_hard.setToggleGroup(difficultyGroup);

        difficultyGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) ->{
            if (newToggle == null){
                oldToggle.setSelected(true);
            } else{
                updateDifficultyInfo();
            }
        });

        btn_normal.setSelected(true);

        viewManager.getPrimaryStage().setOnShown(event -> updateDifficultyInfo());
    }

    private Difficulty selectedDifficulty(){
        return (Difficulty) difficultyGroup.getSelectedToggle().getUserData();
    }

    public void handleStart() {
        viewManager.initGameStage();

        Stage gameStage = viewManager.getGameStage();
        launchSnake(gameStage, selectedDifficulty(), chk_powerUp.isSelected());
        gameStage.setScene(gameScene);
        viewManager.getPrimaryStage().close();
        gameStage.show();
    }

    private void launchSnake(Stage gameStage, Difficulty difficulty, boolean powerUp) {
        Game game = new Game(X_VALUE, Y_VALUE, powerUp, difficulty);
        Obstacle obstacle = new Obstacle(game.getWidth(), game.getHeight());
        Snake snake = new Snake(game.getWidth(), game.getHeight(), game.isNoBorder(), obstacle);
        Coin coin = new RedCoin(game.getWidth(), game.getHeight(), snake, obstacle);

        GameView gameView = new GameView(game);
        GameController controller = new GameController(snake, coin, game, gameView, obstacle, gameStage, Main.getHighScore(), Main :: saveHighScore);

        StackPane gamePane = new StackPane();
        gameScene = new Scene(gamePane, game.getWidth() * game.getCellSize(), game.getHeight() * game.getCellSize());

        gameScene.setOnKeyPressed(controller::handle);

        gameView.makeScene(gameScene, snake, coin, obstacle);
        gameView.attachTo(gamePane);
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    private void updateDifficultyInfo(){
        Difficulty difficulty = selectedDifficulty();
        lbl_difficultyInfo.setText(difficulty.getDescription()
                + "     Personal Best: " + Main.getHighScore().getBest(difficulty));
    }
}
