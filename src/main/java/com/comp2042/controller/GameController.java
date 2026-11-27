package com.comp2042.controller;

import com.comp2042.model.*;
import com.comp2042.model.coin.Coin;
import com.comp2042.model.coin.CoinEffectContext;
import com.comp2042.model.coin.CoinFactory;
import com.comp2042.model.coin.RedCoin;
import com.comp2042.view.GameView;
import com.comp2042.view.Sound;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements CoinEffectContext{
    private Snake snake;
    private Coin coin;
    private GameView gameView;
    private Game game;
    private Stage stage;
    private Obstacle obstacle;

    private KeyCode key = KeyCode.LEFT;
    private KeyCode lastKey = KeyCode.LEFT;
    private boolean keyPressed = false;
    private boolean paused = false;

    private final CoinFactory coinFactory;
    private final Sound sound = new Sound();
    private final HighScore highScore;
    private boolean scoreRecorded = false;

    private final Runnable onNewHighScore;

    public GameController(Snake snake, Coin coin, Game game, GameView gameView, Obstacle obstacle, Stage stage, HighScore highScore, Runnable onNewHighScore) {
        this.snake = snake;
        this.coin = coin;
        this.gameView = gameView;
        this.game = game;
        this.stage = stage;
        this.coinFactory = new CoinFactory(game.getDifficulty());
        this.highScore = highScore;
        this.obstacle = obstacle;
        this.onNewHighScore = onNewHighScore;

        animation.setCycleCount(Animation.INDEFINITE);
        animation.setRate(game.getSpeed());
        gameView.setBestScore(highScore.getBest(game.getDifficulty()));
    }

    private final Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> tick()));

    private void tick(){
        if (snake.isDead()) {
            return;
        } else {
            snake.move(toDirection(key));

            if (!snake.isDead() && snake.ateCoin(coin)) {
                sound.playCoin();
                powerCoin();
                game.incScore();
                if (game.gameWon()) {
                    animation.stop();
                    gameView.drawGrid(coin, snake, obstacle);
                    gameView.gameWon();
                    gameView.updateScoreLabel();
                    recordScoreIfOver();
                    return;
                }
                newCoin();
            }

            keyPressed = false;

            gameView.drawGrid(coin, snake, obstacle);

            gameView.gameWon();
            gameView.gameOver(snake);
            gameView.updateScoreLabel();
            recordScoreIfOver();
        }
    }
    /** converts direction key into movement key */
    private static Direction toDirection(KeyCode code){
        switch (code){
            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
            case RIGHT:
                return Direction.RIGHT;
            default:
                return Direction.LEFT;
        }
    }

    private boolean isGameOver(){
        return snake.isDead() || game.gameWon();
    }

    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code.isArrowKey() && !isGameOver() && !paused){
            boolean turning = valKey(code);

            if (turning || code == key){
                animation.play();
            }

            if (turning && keyPressed){
                lastKey = key;
                key = code;
                keyPressed = true;
                gameView.turnDir(snake, lastKey, key);
            }
        }

        if (!paused && valKey(event.getCode()) && !keyPressed) {
            lastKey = key;
            key = event.getCode();
            keyPressed = true;
            gameView.turnDir(snake, lastKey, key);
        }

        if (event.getCode().equals(KeyCode.P)) {
            togglePaused();
        }

        if (event.getCode().equals(KeyCode.TAB)) {
            reset();
        }

        if (code == KeyCode.PLUS || code == KeyCode.ADD || code == KeyCode.EQUALS){
            gameView.scaleToFullscreen(true, stage, obstacle, snake, coin);
        }

        if (code == KeyCode.MINUS || code == KeyCode.SUBTRACT){
            gameView.scaleToFullscreen(false, stage, obstacle, snake, coin);
        }

        if (event.getCode().equals(KeyCode.ESCAPE)) {
            animation.stop();
            paused = false;
            key = KeyCode.LEFT;
            lastKey = KeyCode.LEFT;
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.getViewManager().getPrimaryStage().show();
            mainMenuController.getViewManager().getGameStage().close();
        }

        if (event.getCode() == KeyCode.M){
            boolean muted = sound.toggleMute();
            gameView.showMuted(muted);
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
        coin = new RedCoin(game.getWidth(), game.getHeight(), snake, obstacle);

        animation.setRate(game.getSpeed());
        gameView.resetTurns();
        key = KeyCode.LEFT;
        lastKey = KeyCode.LEFT;
        keyPressed = false;
        paused = false;
        gameView.showPaused(false);
        game.resetScore();
        obstacle.reset();

        gameView.gameWon();
        gameView.gameOver(snake);
        gameView.updateScoreLabel();

        gameView.drawGrid(coin, snake, obstacle);

        scoreRecorded = false;

        gameView.showNewHighScore(false);
    }

    private void powerCoin() {
        animation.setRate(game.getSpeed());
        snake.ignoreBorders(false);

        coin.applyEffect(this);
        game.addPoints(coin.getPoints());
    }

    private void newCoin() {
        coin = coinFactory.create(game.getWidth(), game.getHeight(), snake, obstacle, game.isPowerUp());
    }

    private void togglePaused(){
        if (isGameOver()){
            return;
        }
        if (paused){
            paused = false;
            animation.play();
        } else if (animation.getStatus() == Animation.Status.RUNNING) {
            paused = true;
            animation.pause();
        }
        gameView.showPaused(paused);
    }

    private void recordScoreIfOver(){
        if  (scoreRecorded || !isGameOver()){
            return;
        }
        scoreRecorded = true;

        boolean newRecord = highScore.submit(game.getDifficulty(), game.getScorePoints());
        if (newRecord){
            onNewHighScore.run();
        }
        gameView.setBestScore(highScore.getBest(game.getDifficulty()));
        gameView.showNewHighScore(newRecord);
    }

    @Override
    public Snake getSnake() {
        return snake;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Obstacle getObstacle() {
        return obstacle;
    }

    @Override
    public void setSpeedBonus(double bonus){
        animation.setRate(game.getSpeed() + bonus);
    }
}
