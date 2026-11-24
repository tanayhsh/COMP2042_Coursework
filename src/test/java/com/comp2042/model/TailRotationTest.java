package com.comp2042.model;

import com.comp2042.view.GameView;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class TailRotationTest {

    @Test
    void tailFacesEachDirectionNormally() {
        Point tail = new Point(5, 5);

        assertEquals(0, GameView.tailRotation(tail, new Point(5, 4)));
        assertEquals(180, GameView.tailRotation(tail, new Point(5, 6)));
        assertEquals(270, GameView.tailRotation(tail, new Point(4, 5)));
        assertEquals(90, GameView.tailRotation(tail, new Point(6, 5)));
    }

    @Test
    void staysFacingRightDirectionAcrossAllEdges(){

        Point leftEdge = new Point(0,5);
        Point rightEdge = new Point(19,5);
        Point topEdge = new Point(5,0);
        Point bottomEdge = new Point(5,19);

        assertEquals(270, GameView.tailRotation(leftEdge, rightEdge));
        assertEquals(90, GameView.tailRotation(rightEdge, leftEdge));
        assertEquals(0, GameView.tailRotation(topEdge, bottomEdge));
        assertEquals(180, GameView.tailRotation(bottomEdge, topEdge));
    }
}
