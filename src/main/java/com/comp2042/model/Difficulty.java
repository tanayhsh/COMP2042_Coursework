package com.comp2042.model;

/** game difficulty levels, each with its own speed and border rule */
public enum Difficulty {
    EASY(8.5, true),
    NORMAL(8.5, false),
    HARD(13, false);

    private final double speed;
    private final boolean noBorder;

    Difficulty(double speed, boolean noBorder){
        this.speed = speed;
        this.noBorder = noBorder;
    }

    /** @return moves per secomd */
    public double getSpeed() {
        return speed;
    }

    /** @return true if snake can wrap around edges instead of dying */
    public boolean isNoBorder() {
        return noBorder;
    }
}
