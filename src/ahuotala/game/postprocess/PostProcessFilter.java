package ahuotala.game.postprocess;

/**
 *
 * @author Aleksi Huotala
 */
public interface PostProcessFilter {

    /**
     * Applies a new effect
     * @param rgb Decimal representation of a color
     * @return Color processed by the PostProcess class
     */
    int applyEffect(int rgb);
}
