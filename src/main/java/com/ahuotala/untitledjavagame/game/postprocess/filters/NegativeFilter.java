package com.ahuotala.untitledjavagame.game.postprocess.filters;

import java.awt.Color;

/**
 * Negative filter
 * @author Aleksi Huotala
 */
public class NegativeFilter implements PostProcessFilter {

    @Override
    public int applyEffect(int rgb) {
        Color c = new Color(rgb);
        int red = 255 - c.getRed();
        int green = 255 - c.getGreen();
        int blue = 255 - c.getBlue();
        return new Color(red, green, blue).getRGB();
    }

}
