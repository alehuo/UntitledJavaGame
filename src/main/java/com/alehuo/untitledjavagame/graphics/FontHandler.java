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

import com.alehuo.untitledjavagame.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author alehuo
 */
public class FontHandler {

    private SpriteSheet spriteSheet;
    private BufferedImage fontImage = null;
    private String[] charMap = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "!", "?", "-", ":", "_", "\"", "#", "@"};
    private BufferedImage[] charset = new BufferedImage[charMap.length];
    private double scale;

    /**
     *
     * @param path
     */
    public FontHandler(String path) {
        this.scale = Game.FONTSCALE;
        try {
            fontImage = ImageIO.read(FontHandler.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fontImage != null) {
            for (int i = 1; i <= 26; i++) {
                charset[i - 1] = fontImage.getSubimage(0, i * 16, 16, 16);
            }
            for (int i = 27; i <= 45; i++) {
                charset[i] = fontImage.getSubimage(16, (i - 26) * 16, 16, 16);
            }
        }
    }

    /**
     *
     * @param g
     * @param text
     * @param x
     * @param y
     */
    public void drawText(Graphics g, String text, int x, int y) {
        text = text.toLowerCase();
        char[] chars = text.toCharArray();
        int offset = 0;
        for (char character : chars) {
            int charIndex = Arrays.asList(charMap).indexOf(character + "");
            g.drawImage(charset[charIndex], x + offset, y, (int) Math.floor(16 * scale), (int) Math.floor(16 * scale), null);
            switch (charIndex) {
                case 19:
                    offset += 13;
                    break;
                case 8:
                    offset += 11;
                    break;
                case 26:
                    offset += 8;
                    break;
                default:
                    offset += 16;
                    break;
            }

        }

    }
}
