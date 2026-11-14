package com.comp2042.model;

import javafx.scene.paint.Color;

public class Game {
    private Color color = Color.web("feffe4");
    private int width;
    private int height;
    private int score;
    private int maxScore;
    private int initialMaxScore;
    private double cellSize;
    private int scorePoints;
    private double speed;
    private boolean powerUp;
    private boolean noBorder;
    private final Difficulty difficulty;

    public Game(int x, int y, boolean powerUp, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.powerUp = powerUp;
        width = x;
        height = y;
        initialMaxScore = x * y - 2;
        maxScore = initialMaxScore;

        speed = difficulty.getSpeed();
        noBorder = difficulty.isNoBorder();

        if (height > 50) {
            cellSize = 10;
        } else {
            cellSize = 20;
        }
    }

    public boolean gameWon() {
        return score == maxScore;
    }

    public void incScore() {
        score++;
    }

    public void decMaxScore(int obstacleCount) {
        this.maxScore -= obstacleCount;
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
        maxScore = initialMaxScore;
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

    public int getMaxScore() {
        return maxScore;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}