/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.graphics.animation;

import ahuotala.game.Tickable;
import java.util.ArrayList;

/**
 *
 * @author Aleksi
 */
public class AnimationTicker implements Tickable {

    private ArrayList<Animation> animations;

    public AnimationTicker() {
        animations = new ArrayList<>();
    }

    public void register(Animation animation) {
        animations.add(animation);
    }

    @Override
    public void tick() {
        if (!animations.isEmpty()) {
            for (Animation animation : animations) {
                animation.tick();
            }
        }

    }
}
