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
package com.alehuo.untitledjavagame.map;

import com.alehuo.untitledjavagame.entities.GameObject;
import com.alehuo.untitledjavagame.entities.InteractableGameObject;
import com.alehuo.untitledjavagame.entities.InteractableNpc;
import com.alehuo.untitledjavagame.entities.Npc;
import java.util.ArrayList;

/**
 *
 * @author alehuo
 */
public class World {

    private ArrayList<Npc> npcs;
    private ArrayList<InteractableNpc> interactableNpcs;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<InteractableGameObject> interactableGameObjects;
    private GameMap map;

    /**
     *
     */
    public World() {
    }

    /**
     *
     * @param map
     */
    public void loadMap(GameMap map) {
        this.map = map;
    }

    /**
     *
     * @param npc
     */
    public void registerNpc(Npc npc) {
        npcs.add(npc);
    }

    /**
     *
     * @param npc
     */
    public void registerNpc(InteractableNpc npc) {
        interactableNpcs.add(npc);
    }

    /**
     *
     * @param obj
     */
    public void registerObject(GameObject obj) {
        gameObjects.add(obj);
    }

    /**
     *
     * @param obj
     */
    public void registerObject(InteractableGameObject obj) {
        interactableGameObjects.add(obj);
    }
}
