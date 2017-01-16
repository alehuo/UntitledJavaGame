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
package com.alehuo.untitledjavagame.postprocess.filters;

import java.awt.Color;

/**
 * Brighten filter
 *
 * @author alehuo
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
