package ahuotala.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author Aleksi Huotala
 */
public class Renderer {

    private final int width;
    private final int height;
    public int[] pixels;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Set pixel color. You can use either the getColor function or hexadecimal input.
                pixels[x + y * width] = getColor(0,0,0);
            }
        }
    }

    /**
     * Set an independent pixel's color
     * @param x
     * @param y
     * @param r
     * @param g
     * @param b 
     */
    public void setColor(int x, int y, int r, int g, int b) {
        int rgb = ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
        pixels[x + y * width] = rgb;
    }

    /**
     * Convert color from RGB to dec
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public int getColor(int r, int g, int b) {
        return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
    }

    public void renderObject(int x, int y, BufferedImage img) {
        //Extract pixel data from an image
        int[] pixelArray = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixelArray.length; i++) {
            pixels[x + y * width + i] = pixelArray[i];
        }
    }
}
