package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.entities.GameObject;
import com.ahuotala.untitledjavagame.entities.InteractableGameObject;
import com.ahuotala.untitledjavagame.entities.InteractableNpc;
import com.ahuotala.untitledjavagame.entities.Npc;
import com.ahuotala.untitledjavagame.map.Map;
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
    private Map map;

    public World() {
    }

    public void loadMap(Map map) {
        this.map = map;
    }

    public void registerNpc(Npc npc) {
        npcs.add(npc);
    }

    public void registerNpc(InteractableNpc npc) {
        interactableNpcs.add(npc);
    }

    public void registerObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void registerObject(InteractableGameObject obj) {
        interactableGameObjects.add(obj);
    }
}
