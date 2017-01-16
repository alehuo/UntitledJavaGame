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
package com.alehuo.untitledjavagame.graphics.animation;

import com.alehuo.untitledjavagame.Tickable;
import java.util.ArrayList;

/**
 *
 * @author alehuo
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
