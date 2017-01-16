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
package com.alehuo.untitledjavagame.entities;

import com.alehuo.untitledjavagame.Tickable;
import java.util.ArrayList;

/**
 *
 * @author alehuo
 */
public class NpcTicker implements Tickable {

    private final ArrayList<Npc> npcs;
    private final ArrayList<InteractableNpc> interactableNpcs;

    /**
     *
     */
    public NpcTicker() {
        npcs = new ArrayList<>();
        interactableNpcs = new ArrayList<>();
    }

    /**
     *
     * @param npc
     */
    public void register(Npc npc) {
        npcs.add(npc);
    }

    /**
     *
     * @param npc
     */
    public void register(InteractableNpc npc) {
        interactableNpcs.add(npc);
    }

    /**
     *
     */
    @Override
    public void tick() {
        if (!npcs.isEmpty()) {
            for (Npc npc : npcs) {
                npc.tick();
            }
        }
        if (!interactableNpcs.isEmpty()) {
            for (InteractableNpc npc : interactableNpcs) {
                npc.tick();
            }
        }
    }

}
