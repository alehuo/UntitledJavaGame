/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.util.ArrayList;

/**
 *
 * @author Aleksi
 */
public class AnimationTicker {

    private ArrayList<Animation> animations;

    public AnimationTicker() {
        animations = new ArrayList<>();
    }

    public void register(Animation animation) {
        animations.add(animation);
    }

    public void tick() {
        if (!animations.isEmpty()) {
            for (Animation animation : animations) {
                animation.tick();
            }
        }

    }
}
