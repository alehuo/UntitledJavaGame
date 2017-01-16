/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.graphics;

import com.alehuo.untitledjavagame.postprocess.filters.NormalFilter;
import com.alehuo.untitledjavagame.postprocess.filters.PostProcessFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alehuo
 */
public class Renderer {
    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    /**
     * Width
     */
    private final int width;

    /**
     * Height
     */
    private final int height;

    /**
     * Pixel array
     */
    private int[] pixels;


    /**
     * Post process filter
     *
     * Default: normal
     */
    private PostProcessFilter[] filters;

    /**
     * @param width Width of the window
     * @param height Height of the window
     * @param pixels Pixel array
     */
    public Renderer(int width, int height, int[] pixels) {
        filters = new PostProcessFilter[64];
        PostProcessFilter n = new NormalFilter();
        filters[0] = n;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    /**
     * Renders the basic image Alpha channel = 0xeb0bff / (235,11,255)
     */
    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = getColor(0, 0, 0);
            }
        }
    }

    /**
     * Clears the pixel array Alpha channel = 0xeb0bff / (235,11,255)
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x9e9e9e;
        }
    }

    /**
     * Set an independent pixel's color (Alpha color = 0xeb0bff / (235,11,255))
     *
     * @param x X -coordinate
     * @param y Y -coordinate
     * @param r Red (0-255)
     * @param g Green (0-255)
     * @param b Blue (0-255)
     */
    public void setColor(int x, int y, int r, int g, int b) {
        int rgb = ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
        pixels[x + y * width] = rgb;
    }

    /**
     * Convert color from RGB to dec (Alpha color = 0xeb0bff / (235,11,255))
     *
     * @param r Red 0-255
     * @param g Green 0-255
     * @param b Blue 0-255
     * @return Color in decimal format
     */
    public int getColor(int r, int g, int b) {
        return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
    }

    /**
     * Renders a sprite Alpha channel = 0xeb0bff / (235,11,255)
     *
     * @param dx X
     * @param dy Y
     * @param sprite Sprite
     */
    public void renderSprite(Sprite sprite, int dx, int dy) {
        if (sprite != null) {
            //Pixel array of the sprite
            int[] spritePixels = sprite.getPixels();
            //Loop through y and x coordinates
            for (int y = 0; y < sprite.getHeight(); y++) {
                for (int x = 0; x < sprite.getWidth(); x++) {
                    // Alpha channel = 0xeb0bff / (235,11,255)
                    // Also make sure that we don't try to write into pixels that don't exist, prevents ArrayIndexOutOfBounds -exception
                    if (spritePixels[x + y * sprite.getWidth()] != 0xeb0bff && x + dx < width & y + dy < height && x + dx > 0 && y + dy > 0) {
                        //Set the pixels
                        pixels[x + dx + (y + dy) * width] = applyFilter(spritePixels[x + y * sprite.getWidth()]);

                    }
                }
            }
        } else {
            LOG.log(Level.SEVERE, "Error rendering sprite, Sprite object can't be NULL");
        }
    }

    /**
     * Returns the pixel array
     *
     * @return Pixel array
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * Returns the height of the Renderer area
     *
     * @return Height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the Renderer area
     *
     * @return Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets a filter to be used
     *
     * @param filters List of filters
     */
    public void setFilters(PostProcessFilter... filters) {
        this.filters = filters;
    }

    /**
     * Resets the PP filter to normal
     */
    public void resetFilter() {
        filters = null;
        filters = new PostProcessFilter[64];
        filters[0] = new NormalFilter();
    }

    /**
     * Returns a color that has been processed by a PP filter
     *
     * @param color Color in decimal format
     * @return Processed color in decimal format
     */
    public int applyFilter(int color) {
        int tmpColor = color;
        if (filters != null && filters.length != 0) {
            for (int i = 0; i < filters.length; i++) {
                if (filters[i] != null) {
                    PostProcessFilter ppF = filters[i];
                    tmpColor = ppF.applyEffect(tmpColor);
                }
            }
            return tmpColor;
        }
        return color;
    }

}
