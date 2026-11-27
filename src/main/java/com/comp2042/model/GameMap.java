package com.comp2042.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GameMap {
    OPEN("Open", "No Walls.", emptyLayout()),

    PILLARS("Pillars", "Four blocks to steer around.", new String[]{
            "....................",
            "....................",
            "....................",
            "...###........###...",
            "...###........###...",
            "...###........###...",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...###........###...",
            "...###........###...",
            "...###........###...",
            "....................",
            "....................",
            "...................."
    }),

    CORRIDORS("Corridors", "Two long walls to split the board into lanes.", new String[]{
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...##############...",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...##############...",
            "....................",
            "....................",
            "....................",
            "....................",
            "...................."
    }),

    BOX("Box", "Wall around the edge with gaps on each side.", new String[]{
            "########....########",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "....................",
            "....................",
            "....................",
            "....................",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "#..................#",
            "########....########"
    }),

    ROOMS("Rooms", "Two walls with doorways dividing the board into rooms.", new String[]{
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "....................",
            "....................",
            "....................",
            "....................",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......",
            "......#......#......"
    }),

    MAZE("Maze", "Rings of walls with gaps. Find your way through.", new String[]{
            "....................",
            "....................",
            "..################..",
            "..#..............#..",
            "..#..............#..",
            "..#..####..####..#..",
            "..#..#........#..#..",
            "..#..#........#..#..",
            "..#..#........#..#..",
            ".....#........#.....",
            ".....#........#.....",
            "..#..#........#..#..",
            "..#..#........#..#..",
            "..#..#........#..#..",
            "..#..####..####..#..",
            "..#..............#..",
            "..#..............#..",
            "..################..",
            "....................",
            "...................."
    });

    public static final int size = 20;

    private final String name;
    private final String description;
    private final List<Point> walls;

    GameMap(String name, String description, String[] layout){
        this.name = name;
        this.description = description;
        this.walls = Collections.unmodifiableList(parse(layout));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Point> getWalls() {
        List<Point> copy = new ArrayList<>();
        for (Point wall: walls){
            copy.add(wall);
        }
        return copy;
    }

    static List<Point> parse(String[] layout){
        if (layout.length != size){
            throw new IllegalArgumentException("Map must have" + size + "rows, but has " + layout.length);
        }
        List<Point> walls = new ArrayList<>();
        for (int y = 0; y < size; y++){
            String row = layout[y];
            if (row.length() != size){
                throw new IllegalArgumentException("Row " + y + " must be " + size + " characters, but is " + row.length());
            }
            for (int x = 0; x < size; x++){
                char cell = row.charAt(x);
                if (cell == '#'){
                    walls.add(new Point(x, y));
                } else if (cell != '.'){
                    throw new IllegalArgumentException("Unknown character '" + cell + "' at (" + x +", " + y +")");
                }
            }
        }
        return walls;
    }

    private static String[] emptyLayout(){
        String[] rows = new String[size];
        Arrays.fill(rows, ".".repeat(size));
        return rows;
    }
}