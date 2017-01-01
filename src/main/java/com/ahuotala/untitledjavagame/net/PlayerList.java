/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net;

import com.ahuotala.untitledjavagame.entities.MpPlayer;
import java.util.HashMap;

/**
 *
 * @author alehuo
 */
public class PlayerList implements java.io.Serializable {

    private HashMap<String, MpPlayer> playerList;

    /**
     *
     */
    public PlayerList() {
        playerList = new HashMap<>();
    }

    /**
     *
     * @param uuid
     * @param mp
     */
    public void updatePlayer(String uuid, MpPlayer mp) {
        playerList.put(uuid, mp);
    }

    /**
     *
     * @param uuid
     */
    public void removePlayer(String uuid) {
        playerList.remove(uuid);
    }

    /**
     *
     * @return
     */
    public HashMap<String, MpPlayer> getPlayerList() {
        return playerList;
    }

}
