package com.comp2042.view;

import com.comp2042.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewManager {

    private static final String mainMenuFXML = "/resources/views/MainMenu.fxml";

    private final Stage primaryStage;
    private AnchorPane root;

    private Stage gameStage;

    private Image snakeLogo = new Image("resources/views/res/SnakeLogoWhite.png");

    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initializeMainMenu() {
        URL location = ViewManager.class.getResource(mainMenuFXML);
        if (location == null){
            throw new IllegalStateException("Main menu FXML not found at " + mainMenuFXML);
        }
        try {
            FXMLLoader loader = new FXMLLoader(location);
            root = loader.load();
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load main menu form " + mainMenuFXML);
        }

        Scene mainScene = new Scene(root);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(snakeLogo);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void initGameStage() {
        gameStage = new Stage();
        gameStage.setTitle("Snake Game");
        gameStage.getIcons().add(snakeLogo);
        gameStage.setResizable(false);
        gameStage.setFullScreenExitHint("Press - to exit full screen");
    }

    public Stage getGameStage() {
        return gameStage;
    }
}
