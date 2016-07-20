/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.map;

import ahuotala.game.Game;
import ahuotala.graphics.Animation;
import ahuotala.graphics.AnimationTicker;
import ahuotala.graphics.SpriteSheet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Aleksi
 */
public class Map {

    private ArrayList<String> lines;
    private HashMap<String, BufferedImage> tiles;
    private HashMap<String, Animation> animations;
    private int scale;
    private int offsetX = 0;
    private int offsetY = 0;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public Map(String name) {
        this.scale = Game.SCALE;
        tiles = new HashMap<>();
        animations = new HashMap<>();
        lines = new ArrayList<>();
        tiles.put("grass", Game.spriteSheet.getSprite("grass", 32, 80, 16));
        tiles.put("house", Game.spriteSheet.getSprite("house", 32, 96, 48, 32));
        tiles.put("water", Game.spriteSheet.getSprite("water", 160, 0, 16));
        animations.put("water_ani", new Animation("Water", Game.spriteSheet, 30));
        Game.animationTicker.register(animations.get("water_ani"));
        try {
            File mapFile = new File("src/ahuotala/map/" + name + ".map");
            Scanner sc = new Scanner(mapFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //Skip comments
                if (line.contains("#") || line.isEmpty()) {
                    continue;
                }
                lines.add(line);
                int x = Integer.parseInt(line.split(",")[0]);
                int y = Integer.parseInt(line.split(",")[1]);
                //Temp solution
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }
            System.out.println("Loaded map file into memory.");
            System.out.println("Min x:" + minX + ", min y:" + minY + ", max x:" + maxX + ", max y:" + maxY);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void renderMap(Graphics g, int offsetX, int offsetY, int playerX, int playerY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        //Parse the map file line by line
        int tileCount = 0;
        for (String line : lines) {
            String[] lineData = line.split(",");

            //tile per tile, add things
            int x = Integer.parseInt(lineData[0]);
            int y = Integer.parseInt(lineData[1]);
            //Performance optimization: don't load tiles we don't need
            int radiusX = 440;
            int radiusY = 250;
            if (x < playerX - radiusX || x > playerX + radiusX || y < playerY - radiusY || y > playerY + radiusY) {
                continue;
            }
            String tileType = lineData[2];
            if (lineData.length != 4) {
                System.err.println("Invalid tile at x = " + x + ",y = " + y);
            }
            int isAnimated = Integer.parseInt(lineData[3]);
            if (isAnimated == 1) {
                if (!animations.containsKey(tileType)) {
                    System.err.println("Animation tile '" + tileType + "' is missing at x = " + x + ",y = " + y);
                }
                animations.get(tileType).nextFrame(g, x + offsetX, y + offsetY);
            } else {
                g.drawImage(tiles.get(tileType), x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, null);
            }
            tileCount++;
        }
        double percentange = (tileCount * 1.0 / lines.size()) * 100 * 1.0;
//        System.out.println(tileCount + " of " + lines.size() + " (" + percentange + "%) tiles rendered this round");
    }

    public void renderObject(Graphics g, int x, int y, String name) {
        g.drawImage(tiles.get(name), x + offsetX, y + offsetY, tiles.get(name).getWidth() * scale, tiles.get(name).getHeight() * scale, null);
    }

}
