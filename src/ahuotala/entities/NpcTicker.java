/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Tickable;
import java.util.ArrayList;

/**
 *
 * @author Aleksi
 */
public class NpcTicker implements Tickable {

    private final ArrayList<Npc> npcs;
    private final ArrayList<InteractableNpc> interactableNpcs;

    public NpcTicker() {
        npcs = new ArrayList<>();
        interactableNpcs = new ArrayList<>();
    }

    public void register(Npc npc) {
        npcs.add(npc);
    }

    public void register(InteractableNpc npc) {
        interactableNpcs.add(npc);
    }

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
