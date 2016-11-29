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

    private HashMap<String, MpPlayer> playerList;

    public PlayerList() {
        playerList = new HashMap<>();
    }

    public void updatePlayer(String uuid, MpPlayer mp) {
        playerList.put(uuid, mp);
    }

    public void removePlayer(String uuid) {
        playerList.remove(uuid);
    }

    public HashMap<String, MpPlayer> getPlayerList() {
        return playerList;
    }

}
