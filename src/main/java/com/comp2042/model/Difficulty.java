package com.comp2042.model;

/** game difficulty levels, each with its own speed and border rule */
public enum Difficulty {
    EASY(8.5, true, "You can wrap around walls. Normal Speed."),
    NORMAL(8.5, false, "You cannot wrap around walls. Normal Speed."),
    HARD(13, false, "You cannot wrap around walls. Fast");

    private final double speed;
    private final boolean noBorder;
    private final String description;

    Difficulty(double speed, boolean noBorder, String description){
        this.speed = speed;
        this.noBorder = noBorder;
        this.description = description;
    }

    /** @return moves per secomd */
    public double getSpeed() {
        return speed;
    }

    /** @return true if snake can wrap around edges instead of dying */
    public boolean isNoBorder() {
        return noBorder;
    }

    public String getDescription() {
        return description;
    }
}
