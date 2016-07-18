/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.map;

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
    private int scale;
    private int offsetX = 0;
    private int offsetY = 0;

    public Map(String name, SpriteSheet spriteSheet, int scale) {
        this.scale = scale;
        blocks = new HashMap<>();
        lines = new ArrayList<>();
        blocks.put("grass", spriteSheet.getSprite("grass", 32, 80, 16));
        blocks.put("house", spriteSheet.getSprite("house", 32, 96, 48, 32));
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

    public void renderMap(Graphics g, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        for (String line : lines) {
            String[] lineData = line.split(",");
            //Block per block, add things
            int x = Integer.parseInt(lineData[0]);
            int y = Integer.parseInt(lineData[1]);
            String blockType = lineData[2];
            g.drawImage(blocks.get(blockType), x + offsetX, y + offsetY, null);
        }
    }

    public void renderObject(Graphics g, int x, int y, String name) {
        g.drawImage(blocks.get(name), x + offsetX, y + offsetY, null);
    }

}
