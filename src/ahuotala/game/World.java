/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.GameObject;
import ahuotala.entities.InteractableGameObject;
import ahuotala.entities.InteractableNpc;
import ahuotala.entities.Npc;
import ahuotala.map.Map;
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
