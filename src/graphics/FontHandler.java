/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Aleksi
 */
public class FontHandler {

    private SpriteSheet spriteSheet;
    private BufferedImage fontImage = null;
    private String[] charMap = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "!", "?", "-", ":", "_", "\"", "#", "@"};
    private BufferedImage[] charset = new BufferedImage[charMap.length];
     
    public FontHandler(String path) {

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

    public void drawText(Graphics g, String text, int x, int y) {
        text = text.toLowerCase();
        char[] chars = text.toCharArray();
        int offset = 0;
        for (char character : chars) {
            int charIndex = Arrays.asList(charMap).indexOf(character + "");
            g.drawImage(charset[charIndex], x + offset, y, null);
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
