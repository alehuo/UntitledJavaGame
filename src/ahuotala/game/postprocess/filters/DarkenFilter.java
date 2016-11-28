/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game.postprocess.filters;

import java.awt.Color;

/**
 * Darken filter
 * @author Aleksi Huotala
 */
public class DarkenFilter implements PostProcessFilter {

    private double factor;

    /**
     * Darken filter
     * @param factor Amount of darkening. The smaller the number the darker the image will be. 
     */
    public DarkenFilter(double factor) {
        this.factor = factor;
    }

    /**
     * Darken filter
     */
    public DarkenFilter() {
        factor = 0.5;
    }

    @Override
    public int applyEffect(int rgb) {

        Color c = new Color(rgb);
        int red = (int) (c.getRed() * factor);
        int green = (int) (c.getGreen() * factor);
        int blue = (int) (c.getBlue() * factor);
        int sum = red + green + blue;
        Color processed = new Color(red, green, blue);
        return processed.getRGB();
    }

}
