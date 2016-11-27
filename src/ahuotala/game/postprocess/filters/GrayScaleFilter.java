package ahuotala.game.postprocess.filters;

import ahuotala.game.postprocess.PostProcessFilter;
import java.awt.Color;

/**
 * Grayscale filter
 * @author Aleksi Huotala
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
