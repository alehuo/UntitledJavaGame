package com.ahuotala.untitledjavagame.game.postprocess.filters;

/**
 * Normal color filter
 * @author Aleksi Huotala
 */
public class NormalFilter implements PostProcessFilter {

    @Override
    public int applyEffect(int rgb) {
        return rgb;
    }

}
