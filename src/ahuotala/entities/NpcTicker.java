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

    private ArrayList<Npc> npcs;

    public NpcTicker() {
        npcs = new ArrayList<>();
    }

    public void register(Npc npc) {
        npcs.add(npc);
    }

    @Override
    public void tick() {
        if (!npcs.isEmpty()) {
            for (Npc npc : npcs) {
                npc.tick();
            }
        }
    }

}
