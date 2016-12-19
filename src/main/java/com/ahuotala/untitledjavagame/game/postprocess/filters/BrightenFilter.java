/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.game.postprocess.filters;

import java.awt.Color;

/**
 * Brighten filter
 *
 * @author Aleksi Huotala
 */
public class BrightenFilter implements PostProcessFilter {

    private double factor;

    /**
     * Brighten filter
     *
     * @param factor Amount of darkening. The smaller the number the darker the
     * image will be.
     */
    public BrightenFilter(double factor) {
        this.factor = factor;
    }

    /**
     * Brighten filter
     */
    public BrightenFilter() {
        factor = 0.5;
    }

    @Override
    public int applyEffect(int rgb) {

        Color c = new Color(rgb);
        int red = (int) Math.round(Math.min(255, c.getRed() + 255 * factor));
        int green = (int) Math.round(Math.min(255, c.getGreen() + 255 * factor));
        int blue = (int) Math.round(Math.min(255, c.getBlue() + 255 * factor));

        Color processed = new Color(red, green, blue);
        return processed.getRGB();
    }

}
