package com.ahuotala.untitledjavagame.graphics.animation;

import com.ahuotala.untitledjavagame.game.Tickable;
import java.util.ArrayList;

/**
 *
 * @author Aleksi
 */
public class AnimationTicker implements Tickable {

    private final ArrayList<Animation> animations;

    /**
     *
     */
    public AnimationTicker() {
        animations = new ArrayList<>();
    }

    /**
     *
     * @param animation
     */
    public void register(Animation animation) {
        animations.add(animation);
    }

    /**
     *
     */
    @Override
    public void tick() {
        if (!animations.isEmpty()) {
            for (Animation animation : animations) {
                animation.tick();
            }
        }

    }
}
