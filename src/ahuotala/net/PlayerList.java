/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.net;

import java.util.HashMap;

/**
 *
 * @author alehuo
 */
public class PlayerList implements java.io.Serializable {

    private HashMap<String, MpPlayer> playerPos;

    public PlayerList() {
        playerPos = new HashMap<>();
    }

    public void updatePlayer(String uuid, MpPlayer mp) {
        playerPos.put(uuid, mp);
    }

    public void removePlayer(String uuid) {
        playerPos.remove(uuid);
    }

}
