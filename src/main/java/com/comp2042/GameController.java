package com.comp2042;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class GameController {
    private Snake snake;
    private Coin coin;
    private GameView gameView;
    private Game game;
    private Stage stage;
    private Obstacle obstacle;

    public static KeyCode key = KeyCode.LEFT;
    public static KeyCode lastKey = KeyCode.LEFT;
    private boolean keyPressed = false;

    public GameController(Snake snake, Coin coin, Game game, GameView gameView, Obstacle obstacle, Stage stage) {
        this.snake = snake;
        this.coin = coin;
        this.gameView = gameView;
        this.game = game;
        this.stage = stage;

        this.obstacle = obstacle;
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setRate(game.getSpeed());
    }

    private final Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (snake.isDead()) {
            return;
        } else {

            if (key.equals(KeyCode.LEFT)) {
                snake.move('L');
            } else if (key.equals(KeyCode.RIGHT)) {
                snake.move('R');
            } else if (key.equals(KeyCode.UP)) {
                snake.move('U');
            } else if (key.equals(KeyCode.DOWN)) {
                snake.move('D');
            }

            if (!snake.isDead() && snake.ateCoin(coin)) {
                powerCoin();
                game.incScore();
                if (game.gameWon()) {
                    gameView.drawGrid(coin, snake, obstacle);
                    return;
                }
                newCoin();
            }

            keyPressed = false;

            if (!snake.isDead() || snake.hitSelf()) {
                gameView.drawGrid(coin, snake, obstacle);
            }

            gameView.gameWon();
            gameView.gameOver(snake);
            gameView.updateScoreLabel();
        }
    }));

    public void handle(KeyEvent event) {
        if (event.getCode().isArrowKey()) animation.play();

        if (valKey(event.getCode()) && !keyPressed) {
            lastKey = key;
            key = event.getCode();
            keyPressed = true;
            gameView.turnDir(snake);
        }

        if (event.getCode().equals(KeyCode.P)) {
            animation.stop();
        }

        if (event.getCode().equals(KeyCode.TAB)) {
            reset();
            gameView.scoreLabel.setText("Score: " + game.getScorePoints());
        }

        KeyCode code = event.getCode();
        if (code == KeyCode.PLUS || code == KeyCode.ADD || code == KeyCode.EQUALS){
            gameView.scaleToFullscreen(true, stage, obstacle, snake, coin);
            gameView.gameOverLabel.setStyle("-fx-font-size: 50;");
            gameView.gameWonLabel.setStyle("-fx-font-size: 50;");
            gameView.scoreLabel.setStyle("-fx-font-size: 35;");
        }

        if (code == KeyCode.MINUS || code == KeyCode.SUBTRACT){
            gameView.scaleToFullscreen(false, stage, obstacle, snake, coin);
            gameView.gameOverLabel.setStyle("-fx-font-size: 15;");
            gameView.gameWonLabel.setStyle("-fx-font-size: 15;");
            gameView.scoreLabel.setStyle("-fx-font-size: 15;");
        }

        if (event.getCode().equals(KeyCode.ESCAPE)) {
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.getViewManager().getPrimaryStage().show();
            mainMenuController.getViewManager().getGameStage().close();
        }
    }

    private boolean valKey(KeyCode newKey) {
        if (!newKey.isArrowKey()) return false;

        // replaced the else block with the switch case to improve readability and also fixed the UP DOWN illegal direct direction reversal
        return switch (newKey) {
            case LEFT, RIGHT -> key != KeyCode.LEFT && key != KeyCode.RIGHT;
            case UP, DOWN ->  key != KeyCode.UP && key != KeyCode.DOWN;
            default -> false;
        };
    }

    private void reset() {

        animation.jumpTo(Duration.ZERO);
        animation.stop();

        snake = new Snake(game.getWidth(), game.getHeight(), game.isNoBorder(), obstacle);
        coin = new Coin(game.getWidth(), game.getHeight(), snake, obstacle);

        animation.setRate(game.getSpeed());
        lastKey = KeyCode.LEFT;
        gameView.resetTurns();
        key = KeyCode.LEFT;
        keyPressed = false;
        game.resetScore();
        obstacle.reset();

        gameView.gameWon();
        gameView.gameOver(snake);
        gameView.updateScoreLabel();

        gameView.drawGrid(coin, snake, obstacle);
    }

    private void powerCoin() {
        animation.setRate(game.getSpeed());
        snake.ignoreBorders(false);
        if (coin.getClass().equals(RainbowCoin.class)) {
            RainbowCoin tempCoin = (RainbowCoin) coin;
            animation.setRate(game.getSpeed() + tempCoin.getSpeedBonus());
        } else if (coin.getClass().equals(SilverCoin.class)){
            Random rnd = new Random();
            obstacle.newObstacles(snake, coin, rnd.nextInt(game.getWidth() * game.getHeight()/ 20) +3);
            game.decMaxScore(obstacle.getObstacles().size());
        } else if (coin.getClass().equals(BlueCoin.class)) {
            snake.ignoreBorders(true);
        }
        game.addPoints(coin.getPoints());
    }

    private void newCoin() {
        if (game.isPowerUp()) {
            Random rnd = new Random();
            int ran = rnd.nextInt(12);
            if (ran == 1) {
                coin = new GoldCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 2) {                                                          // changed to ran == 2 so coins can be selected properly
                coin = new RainbowCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 3) {
                coin = new BlueCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 4 && obstacle.getObstacles().size() == 0) {
                coin = new SilverCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else {
                coin = new Coin(game.getWidth(), game.getHeight(), snake, obstacle);
            }
        } else {
            coin = new Coin(game.getWidth(), game.getHeight(), snake, obstacle);
        }
    }
}
