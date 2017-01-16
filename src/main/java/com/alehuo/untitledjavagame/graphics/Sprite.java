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

/**
 * Sprite
 *
 * @author alehuo
 */
public class Sprite {

    /**
     * Pixel array of the sprite
     */
    private int[] pixels;

    /**
     * Width of the sprite
     */
    private int width;

    /**
     * Height of the sprite
     */
    private int height;

    /**
     * Sprite
     * @param width Widht of the sprite
     * @param height Height of the sprite
     * @param pixels Pixel array of the sprite
     */
    public Sprite(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    /**
     * Returns the pixel array of the sprite
     * @return Pixel array
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * Sets the pixel array of the sprite
     * @param pixels Pixel array
     */
    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    /**
     * Returns the width of the sprite
     * @return Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the sprite
     * @return Height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the width of the sprite
     * @param width Width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the height of the sprite
     * @param height Height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
