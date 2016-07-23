/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.graphics.animation;

import ahuotala.game.Game;
import ahuotala.graphics.SpriteSheet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Aleksi
 */
public final class Animation {

    private ArrayList<BufferedImage> frames;
    //Interval in frames
    private int interval;
    //Count
    private int count = 0;
    //Scale
    private int scale;
    //Spritesheet
    private SpriteSheet spritesheet;
    //Name
    private String name;
    //Index
    private int index = 0;

    public Animation(String name, SpriteSheet spritesheet, int interval) {
        this.frames = new ArrayList<>();
        this.interval = interval;
        this.scale = Game.SCALE;
        this.spritesheet = spritesheet;
        this.name = name;
        this.Load();
    }

    public void tick() {
        count++;
    }

    public void nextFrame(Graphics g, int x, int y) {
        if (count >= interval * frames.size()) {
            count = 0;
        }
        index = (int) Math.floor(count / interval);
        g.drawImage(frames.get(index), x, y, frames.get(index).getWidth() * scale, frames.get(index).getHeight() * scale, null);
    }

    public int getWidth() {
        return frames.get(index).getWidth();
    }

    public int getHeight() {
        return frames.get(index).getHeight();
    }

    /**
     * Load an animation from an .ani file
     */
    public void Load() {
        frames = new ArrayList<>();
        try {
            URL url = getClass().getResource(name + ".ani");
            File animFile = new File(url.getPath());
            try (Scanner sc = new Scanner(animFile)) {
                int frameCount = 0;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }
                    //Skip comments
                    String[] lineData;
                    lineData = line.split(",");
                    int x = Integer.parseInt(lineData[0]);
                    int y = Integer.parseInt(lineData[1]);
                    int width = Integer.parseInt(lineData[2]);
                    int height = Integer.parseInt(lineData[3]);
                    //Add the frame
                    frames.add(spritesheet.getSprite("animation_" + name + "_frame" + frameCount, x, y, width, height));
                    frameCount++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
