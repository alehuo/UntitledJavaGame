package com.ahuotala.untitledjavagame.graphics.animation;

import com.ahuotala.untitledjavagame.Game;
import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.Tickable;
import com.ahuotala.untitledjavagame.graphics.Sprite;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Animation class
 *
 * @author Aleksi Huotala
 */
public final class Animation implements Tickable {
    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Animation.class.getName());

    /**
     * List of sprites that make up the animation
     */
    private ArrayList<Sprite> frames;

    /**
     * Frame interval
     */
    private final int interval;

    /**
     * Current tick count
     */
    private int count = 0;

    /**
     * Current animation name
     */
    private final String name;

    /**
     * Current frame index
     */
    private int index = 0;


    /**
     * Animation constructor
     *
     * @param name Animation name
     * @param interval Animation interval in ticks
     */
    public Animation(String name, int interval) {
        this.frames = new ArrayList<>();
        this.interval = interval * (int) Math.ceil(Game.tickrate / 60);
        this.name = name;
        this.Load();
    }

    /**
     * Tick method as we implement the Tickable -interface
     */
    @Override
    public void tick() {
        count++;
    }

    /**
     * Renders the next frame in order
     *
     * @param r Renderer
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void nextFrame(Renderer r, int x, int y) {
        if (count >= interval * frames.size()) {
            count = 0;
        }
        index = (int) Math.floor(count / interval);
        r.renderSprite(frames.get(index), x, y);
    }

    /**
     * Returns the width of the current frame
     *
     * @return Current frame width
     */
    public int getWidth() {
        return frames.get(index).getWidth();
    }

    /**
     * Returns the height of the current frame
     *
     * @return Current frame height
     */
    public int getHeight() {
        return frames.get(index).getHeight();
    }

    /**
     * Load an animation from an .ani file
     */
    public void Load() {
        frames = new ArrayList<>();
        String line = "";
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("animations/" + name + ".ani");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            int frameCount = 0;
            if (stream != null) {
                while ((line = reader.readLine()) != null) {
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
                    frames.add(Game.spriteSheet.loadSprite("animation_" + name + "_frame" + frameCount, x, y, width, height));
                    frameCount++;
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }

    }
}
