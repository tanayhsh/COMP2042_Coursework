package com.comp2042.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void directionsAreCorrect(){
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
        assertFalse(Direction.UP.isOpposite(Direction.UP));
    }

    @Test
    void eachDirectionMovesExactlyOneCell(){
        for (Direction d : Direction.values()){
            assertEquals(1, Math.abs(d.getDx()) + Math.abs(d.getDy()));
        }
    }

}
