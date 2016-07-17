/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Aleksi
 */
public class Animation {

    private ArrayList<BufferedImage> frames;
    //Framecount
    private int framecount;
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

    public Animation(String name, SpriteSheet spritesheet, int interval, int scale) {
        this.frames = new ArrayList<>();
        this.interval = interval;
        this.scale = scale;
        this.spritesheet = spritesheet;
        this.name = name;
        this.Load();
    }

    public void getFrame() {

    }

    public void tick() {
        count++;
    }

    public void nextFrame(Graphics g, int x, int y) {
        if (count >= interval * frames.size()) {
            count = 0;
        }
        int index = (int) Math.floor(count / interval);
        g.drawImage(frames.get(index), x, y, frames.get(index).getWidth() * scale, frames.get(index).getHeight() * scale, null);
    }

    /**
     * Load an animation from an .ani file
     */
    public void Load() {
        frames = new ArrayList<>();
        try {
            File animFile = new File("src/graphics/animations/" + name + ".ani");
            Scanner sc = new Scanner(animFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("#") || line.isEmpty()) {
                    continue;
                }
                //Skip comments
                String[] lineData = line.split(",");
                int x = Integer.parseInt(lineData[0]);
                int y = Integer.parseInt(lineData[1]);
                int width = Integer.parseInt(lineData[2]);
                int height = Integer.parseInt(lineData[3]);
                //Add the frame
                frames.add(spritesheet.getSprite(x, y, width, height));
            }
            sc.close();
            this.framecount = frames.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
