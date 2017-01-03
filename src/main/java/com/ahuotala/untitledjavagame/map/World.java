package com.ahuotala.untitledjavagame.map;

import com.ahuotala.untitledjavagame.entities.GameObject;
import com.ahuotala.untitledjavagame.entities.InteractableGameObject;
import com.ahuotala.untitledjavagame.entities.InteractableNpc;
import com.ahuotala.untitledjavagame.entities.Npc;
import java.util.ArrayList;

/**
 *
 * @author Aleksi Huotala
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
