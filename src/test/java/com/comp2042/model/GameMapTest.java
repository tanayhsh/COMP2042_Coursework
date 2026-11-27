package com.comp2042.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest{

    private static final int size = 20;
    private static final Point start_head = new Point(size / 2, size / 2);
    private static final Point start_body = new Point(size/2 + 1, size / 2);
    private static final int safe_dist = 3;

    @Test
    void openMapHasNoWalls(){
        assertTrue(GameMap.OPEN.getWalls().isEmpty());
    }

    @Test
    void everyOtherMapHasWalls(){
        for (GameMap map: GameMap.values()){
            if (map != GameMap.OPEN){
                assertFalse(map.getWalls().isEmpty(), map + "has no walls");
            }
        }
    }

    @Test
    void wallsAreInsideTheGrid(){
        for (GameMap map : GameMap.values()){
            for (Point wall : map.getWalls()){
                assertTrue(wall.x >= 0 && wall.y >= 0 && wall.x < size && wall.y < size,
                        map + " has a wall off the grid at " + wall);
            }
        }
    }

    @Test
    void startingAreaIsClearForEveryMap(){
        for (GameMap map : GameMap.values()){
            for (Point wall : map.getWalls()){
                int distance = Math.abs(wall.x - start_head.x) + Math.abs(wall.y - start_head.y);
                assertTrue(distance > safe_dist, map + " has a wall too close to the start: " + wall);
                assertNotEquals(start_body, wall, map + "has a wall on the starting body");
            }
        }
    }

    @Test
    void everyFreeCellIsReachableOnEveryMap(){
        for (GameMap map : GameMap.values()){
            Set<Point> walls = new HashSet<>(map.getWalls());
            int freeCells = size * size - walls.size();
            assertEquals(freeCells, reachableFrom(start_head, walls).size(),
                    map + " has free cells the snake cannot reach");
        }
    }

    @Test
    void getWallsReturnsCopy(){
        List<Point> walls = GameMap.PILLARS.getWalls();
        int original = walls.size();
        walls.clear();
        assertEquals(original, GameMap.PILLARS.getWalls().size());
    }

    @Test
    void layoutWithUnknownCharsIsRejected(){
        String[] layout = new String[size];
        java.util.Arrays.fill(layout, ",".repeat(size));
        layout[0] = "X" + ".".repeat(size - 1);
        assertThrows(IllegalArgumentException.class, () -> GameMap.parse(layout));
    }

    private  static Set<Point> reachableFrom(Point start, Set<Point> walls){
        Set<Point> seen = new HashSet<>();
        Deque<Point> toVisit = new ArrayDeque<>();
        seen.add(start);
        toVisit.add(start);
        while (!toVisit.isEmpty()){
            Point current = toVisit.poll();
            for (Direction direction: Direction.values()){
                Point next = new Point(current.x + direction.getDx(), current.y + direction.getDy());
                boolean onGrid = next.x >= 0 && next.y >= 0 && next.x < size && next.y < size;
                if (onGrid && !walls.contains(next) && seen.add(next)){
                    toVisit.add(next);
                }
            }
        }
        return seen;
    }
}
