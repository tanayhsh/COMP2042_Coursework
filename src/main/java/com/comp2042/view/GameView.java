package com.comp2042.view;

import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import com.comp2042.model.coin.Coin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;

import java.awt.*;

public class GameView {

    private Canvas gridCanvas;
    private GraphicsContext gc;

    private Label gameOverLabel;
    private Label gameWonLabel;
    private Label scoreLabel;
    private Label pausedLabel;
    private Label muteLabel;
    private boolean gameOverVis = false;
    private boolean gameWonVis = false;

    private Game game;
    private double initX;
    private double initY;
    private double fullScale = 1;
    private boolean fullsize = false;
    private final int[][] turnArray;
    private final EntityManager entity = new EntityManager();

    public GameView(Game game) {
        this.game = game;
        this.turnArray =new int[game.getWidth()][game.getHeight()];
    }

    public void makeScene(Scene scene, Snake snake, Coin coin, Obstacle obstacle) {
        Scene gameScene = scene;
        gridCanvas = new Canvas(gameScene.getWidth(), gameScene.getHeight());
        initX = scene.getWidth();
        initY = scene.getHeight();
        gc = gridCanvas.getGraphicsContext2D();
        drawGrid(coin, snake, obstacle);

        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setId("gameOverLabel");
        String css = "resources/views/styling.css";
        gameOverLabel.getStylesheets().add(css);
        gameOverLabel.setVisible(false);

        gameWonLabel = new Label("YOU WON!");
        gameWonLabel.setId("gameWonLabel");
        gameWonLabel.getStylesheets().add(css);
        gameWonLabel.setVisible(false);

        scoreLabel = new Label("Score: " + game.getScorePoints());
        scoreLabel.setId("scoreLabel");
        scoreLabel.getStylesheets().add(css);
        scoreLabel.setFont(new Font("Arial", 20));
        scoreLabel.setVisible(true);

        pausedLabel = new Label("Paused");
        pausedLabel.setFont(new Font("Ariel", 20));
        pausedLabel.setVisible(false);

        muteLabel = new Label("Mute");
        muteLabel.setStyle("-fx-font-size: 16;");
        muteLabel.setVisible(false);
    }

    public void showPaused(boolean paused){
        pausedLabel.setVisible(paused);
    }

    public void showMuted(boolean muted){
        muteLabel.setVisible(muted);
    }

    public void scaleToFullscreen(boolean fullScreen, Stage primaryStage, Obstacle obstacle, Snake snake, Coin coin) {
        primaryStage.setFullScreen(fullScreen);

        double heightScale = Screen.getPrimary().getVisualBounds().getHeight() / initY;
        double widthScale = Screen.getPrimary().getVisualBounds().getWidth() / initX;

        if (fullScreen && !fullsize) {
            fullScale = Math.min(heightScale,widthScale);
            gridCanvas.setWidth(gridCanvas.getWidth() * fullScale);
            gridCanvas.setHeight(gridCanvas.getHeight() * fullScale);
            fullsize = true;
        } else if (!fullScreen) {
            fullScale = 1;
            gridCanvas.setWidth(initX);
            gridCanvas.setHeight(initY);
            fullsize = false;
        }

        String bigLabel = fullScreen ? "fx-font-size 50;" : "fx-font-size-15";
        String scoreSize = fullScreen ? "fx-font-size 35;" : "fx-font-size-15";
        gameOverLabel.setStyle(bigLabel);
        gameWonLabel.setStyle(bigLabel);
        scoreLabel.setStyle(scoreSize);

        drawGrid(coin, snake, obstacle);
    }

    public void drawGrid(Coin coin, Snake snake, Obstacle obstacle) {
        gc.setFill(game.getColor());
        gc.fillRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());

        gc.drawImage(entity.getCoinImages(coin.getType()), (coin.getPos().x * game.getCellSize()) * fullScale, (coin.getPos().y * game.getCellSize()) * fullScale, game.getCellSize() * fullScale - 3 * fullScale, game.getCellSize() * fullScale - 3 * fullScale);

        List<Point> body = snake.getBody();
        for (int i = 0; i < body.size(); i++){
            drawBody(snake, body, body.get(i), i);
        }

        drawHead(snake);

        gc.setFill(obstacle.getColor());
        for (Point point : obstacle.getObstacles()) {
            gc.fillRect(point.x * game.getCellSize() * fullScale, point.y * game.getCellSize() * fullScale, game.getCellSize() * fullScale, game.getCellSize() * fullScale);
        }
    }

    private void drawHead(Snake snake) {
        entity.getHead().setRotate(snake.getSnakeDir());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = entity.getHead().snapshot(params, null);
        gc.drawImage(rotatedImage, (snake.getHead().x * game.getCellSize()) * fullScale, (snake.getHead().y * game.getCellSize()) * fullScale, game.getCellSize() * fullScale, game.getCellSize() * fullScale);
    }

    private void drawBody(Snake snake, List<Point> body, Point point, int index) {
        Image rotatedImage;
        if (body.size() <= 1) {
            entity.getTail().setRotate(snake.getSnakeDir());
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            rotatedImage = entity.getTail().snapshot(params, null);
        } else if (index == 0) {
            int rotateTail;
            if (body.get(1).getX() == body.get(0).getX() && body.get(1).getY() == body.get(0).getY() - 1) {
                rotateTail = 0;
            } else if (body.get(1).getX() == body.get(0).getX() && body.get(1).getY() == body.get(0).getY() + 1) {
                rotateTail = 180;
            } else if (body.get(1).getX() == body.get(0).getX() - 1 && body.get(1).getY() == body.get(0).getY()) {
                rotateTail = 270;
            } else {
                rotateTail = 90;
            }
            entity.getTail().setRotate(rotateTail);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            rotatedImage = entity.getTail().snapshot(params, null);
        } else if (index > 0 && (turnArray[(int) body.get(index).getX()][(int) body.get(index).getY()] != 0)) {
            entity.getTurn().setRotate(90 * turnArray[(int) body.get(index).getX()][(int) body.get(index).getY()]);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            rotatedImage = entity.getTurn().snapshot(params, null);
        } else {
            entity.getBody().setRotate(snake.getBodyDir(index));
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            rotatedImage = entity.getBody().snapshot(params, null);
        }

        if (turnArray[(int) body.get(0).getX()][(int) body.get(0).getY()] != 0) {
            turnArray[(int) body.get(0).getX()][(int) body.get(0).getY()] = 0;
        }

        gc.drawImage(rotatedImage, (point.x * game.getCellSize()) * fullScale, (point.y * game.getCellSize()) * fullScale, game.getCellSize() * fullScale, game.getCellSize() * fullScale);
    }

    public void turnDir(Snake snake,  KeyCode from, KeyCode to) {

        int x = snake.getHead().x;
        int y = snake.getHead().y;
        
        if ((from == KeyCode.LEFT && to == KeyCode.UP) || (from == KeyCode.DOWN && to == KeyCode.RIGHT)) {
            turnArray[x][y] = 2;
        } else if ((from == KeyCode.LEFT && to == KeyCode.DOWN) || (from == KeyCode.UP && to == KeyCode.RIGHT)) {
            turnArray[x][y] = 3;
        } else if ((from == KeyCode.RIGHT && to == KeyCode.UP) || (from == KeyCode.DOWN && to == KeyCode.LEFT)) {
            turnArray[x][y] = 1;
        } else if ((from == KeyCode.UP && to == KeyCode.LEFT) || (from == KeyCode.RIGHT && to == KeyCode.DOWN)) {
            turnArray[x][y] = 4;
        } else turnArray[x][y] = 0;
    }

    public void gameOver(Snake snake) {
        if (snake.isDead() && !gameOverVis) {
            gameOverVis = true;
            gameOverLabel.setVisible(true);
        } else if (!snake.isDead() && gameOverVis) {
            gameOverLabel.setVisible(false);
            gameOverVis = false;
        }
    }

    public void gameWon() {
        if (game.gameWon() && !gameWonVis) {
            gameWonVis = true;
            gameWonLabel.setVisible(true);
        } else if (!game.gameWon() && gameWonVis) {
            gameWonVis = false;
            gameWonLabel.setVisible(false);
        }
    }

    public void updateScoreLabel() {
        scoreLabel.setText("Score: " + game.getScorePoints());
    }

    public void resetTurns(){
        for (int[] coloumn : turnArray){
            java.util.Arrays.fill(coloumn, 0);
        }
    }

    public void attachTo(StackPane pane){
        pane.getChildren().addAll(gridCanvas, gameOverLabel, gameWonLabel, scoreLabel, pausedLabel, muteLabel);
        pane.setPadding(new Insets(20));
        StackPane.setAlignment(gameOverLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(gameWonLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(scoreLabel, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(pausedLabel, Pos.CENTER);
        StackPane.setAlignment(muteLabel, Pos.TOP_RIGHT);

    }
}
