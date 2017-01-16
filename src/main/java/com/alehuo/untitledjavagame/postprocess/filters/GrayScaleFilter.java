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
 * Grayscale filter
 * @author alehuo
 */
public class GrayScaleFilter implements PostProcessFilter {

    @Override
    public int applyEffect(int rgb) {
        Color c = new Color(rgb);
        int red = (int) (c.getRed() * 0.21);
        int green = (int) (c.getGreen() * 0.72);
        int blue = (int) (c.getBlue() * 0.07);
        int sum = red + green + blue;
        Color processed = new Color(sum, sum, sum);
        return processed.getRGB();
    }
}
