/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game.postprocess.filters;

import java.awt.Color;

/**
 * Hue filter
 *
 * @author Aleksi Huotala
 */
public class HSBFilter implements PostProcessFilter {

    /**
     * Hue
     */
    float hue;
    /**
     * Saturation
     */
    float saturation;
    /**
     * Brightness
     */
    float brightness;

    /**
     * HSL filter
     *
     * @param hue Hue between 0 and 360
     * @param saturation Saturation between 0 and 1
     * @param brightness Brightness between 0 and 1
     */
    public HSBFilter(int hue, float saturation, float brightness) {
        if (hue > 360 || hue < 0) {
            throw new IllegalArgumentException("HUE must be between 0 and 360.");
        }

        if (saturation > 1 || saturation < 0) {
            throw new IllegalArgumentException("Saturation must be between 0 and 1.");
        }

        if (brightness > 1 || brightness < 0) {
            throw new IllegalArgumentException("Brightness must be between 0 and 1.");
        }

        this.hue = hue / 360.0f;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    @Override
    public int applyEffect(int rgb) {
        Color c = new Color(rgb);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        float hsv[] = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        int c2 = Color.getHSBColor(hue, saturation, brightness).getRGB();
        return c2;
    }

}
