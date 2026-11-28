package com.comp2042.model;

import javafx.scene.paint.Color;

public class Game {
    private Color color = Color.web("feffe4");
    private int width;
    private int height;
    private int score;
    private int coinsToWin;
    private int initialCoinsToWin;
    private double cellSize;
    private int scorePoints;
    private double speed;
    private boolean powerUp;
    private boolean noBorder;
    private final Difficulty difficulty;
    private final GameMap map;


    public Game(int x, int y, boolean powerUp, Difficulty difficulty){
        this(x, y, powerUp, difficulty, GameMap.OPEN);
    }

    public Game(int x, int y, boolean powerUp, Difficulty difficulty, GameMap map) {
        if (!map.getWalls().isEmpty() && (x != GameMap.size || y != GameMap.size)){
            throw new IllegalArgumentException("Maps are drawn for a " + GameMap.size + "x" + GameMap.size + " board");
        }

        this.difficulty = difficulty;
        this.map = map;
        this.powerUp = powerUp;
        width = x;
        height = y;
        initialCoinsToWin = x * y - 2 - map.getWalls().size();
        coinsToWin = initialCoinsToWin;

        speed = difficulty.getSpeed();
        noBorder = difficulty.isNoBorder();

        if (height > 50) {
            cellSize = 10;
        } else {
            cellSize = 20;
        }
    }

    public boolean gameWon() {
        return score == coinsToWin;
    }

    public void incScore() {
        score++;
    }

    public void reduceCoinsToWin(int obstacleCount) {
        this.coinsToWin -= obstacleCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void resetScore() {
        score = 0;
        scorePoints = 0;
        coinsToWin = initialCoinsToWin;
    }

    public Color getColor() {
        return color;
    }

    public double getCellSize() {
        return cellSize;
    }

    public void addPoints(int n) {
        scorePoints += n;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public boolean isPowerUp() {
        return powerUp;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isNoBorder() {
        return noBorder;
    }

    public int getCoinsToWin() {
        return coinsToWin;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameMap getMap() {
        return map;
    }
}