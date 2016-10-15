package ahuotala.graphics.animation;

import ahuotala.game.Game;
import ahuotala.game.Renderer;
import ahuotala.graphics.Sprite;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Aleksi Huotala
 */
public final class Animation {

    private ArrayList<Sprite> frames;
    //Interval in frames
    private final int interval;
    //Count
    private int count = 0;
    //Name
    private final String name;
    //Index
    private int index = 0;

    public Animation(String name, int interval) {
        this.frames = new ArrayList<>();
        this.interval = interval * (int) Math.ceil(Game.tickrate / 60);
        this.name = name;
        this.Load();
    }

    public void tick() {
        count++;
    }

    public void nextFrame(Renderer r, int x, int y) {
        if (count >= interval * frames.size()) {
            count = 0;
        }
        index = (int) Math.floor(count / interval);
//        r.renderSprite(r, x, y, frames.get(index));
//        g.drawImage(frames.get(index), x, y, frames.get(index).getWidth(), frames.get(index).getHeight(), null);
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
        String line = "";
        try {
            InputStream stream = getClass().getResourceAsStream(name + ".ani");
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
                    frames.add(Game.spriteSheet.getSprite("animation_" + name + "_frame" + frameCount, x, y, width, height));
                    frameCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
