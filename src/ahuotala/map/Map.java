/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.map;

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
    private HashMap<String, BufferedImage> blocks;
    private HashMap<String, Animation> animations;
    private int scale;
    private int offsetX = 0;
    private int offsetY = 0;

    public Map(String name, AnimationTicker ticker, SpriteSheet spriteSheet, int scale) {
        this.scale = scale;
        blocks = new HashMap<>();
        animations = new HashMap<>();
        lines = new ArrayList<>();
        blocks.put("grass", spriteSheet.getSprite("grass", 32, 80, 16));
        blocks.put("house", spriteSheet.getSprite("house", 32, 96, 48, 32));
        blocks.put("water", spriteSheet.getSprite("water", 160, 0, 16));
        animations.put("water_ani", new Animation("Water", spriteSheet, 30, 1));
        ticker.register(animations.get("water_ani"));
        try {
            File levelFile = new File("src/ahuotala/map/" + name + ".map");
            Scanner sc = new Scanner(levelFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //Skip comments
                if (line.contains("#") || line.isEmpty()) {
                    continue;
                }
                lines.add(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void renderMap(Graphics g, int offsetX, int offsetY, int playerX, int playerY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        //Parse the map file line by line
        for (String line : lines) {
            String[] lineData = line.split(",");

            //Block per block, add things
            int x = Integer.parseInt(lineData[0]);
            int y = Integer.parseInt(lineData[1]);
            //Performance optimization: don't load blocks we don't need
            if (x < playerX - 400 || x > playerX + 400 || y < playerY - 250 || y > playerY + 250) {
                continue;
            }
            String blockType = lineData[2];
            if (lineData.length != 4) {
                System.err.println("Invalid map block at x = " + x + ",y = " + y);
            }
            int isAnimated = Integer.parseInt(lineData[3]);
            if (isAnimated == 1) {
                if (!animations.containsKey(blockType)) {
                    System.err.println("Animation block '" + blockType + "' missing at x = " + x + ",y = " + y);
                }
                animations.get(blockType).nextFrame(g, x + offsetX, y + offsetY);
            } else {
                g.drawImage(blocks.get(blockType), x + offsetX, y + offsetY, null);
            }

        }
    }

    public void renderObject(Graphics g, int x, int y, String name) {
        g.drawImage(blocks.get(name), x + offsetX, y + offsetY, null);
    }

}
