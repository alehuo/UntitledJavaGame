package ahuotala.game.postprocess;

import ahuotala.game.Renderer;

/**
 *
 * @author Aleksi
 */
public class PostProcess {

    private Renderer renderer;

    public PostProcess(Renderer r) {
        renderer = r;
    }

    public void applyFilter(PostProcessFilter f) {
        int[] pixelArray = renderer.getPixels();
        for (int x = 0; x < renderer.getWidth(); x++) {
            for (int y = 0; y < renderer.getHeight(); y++) {
                pixelArray[x + y * renderer.getWidth()] = f.applyEffect(pixelArray[x + y * renderer.getWidth()]);
            }
        }
    }

}
