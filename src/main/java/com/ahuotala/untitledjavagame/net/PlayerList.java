/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net;

import com.ahuotala.untitledjavagame.entities.MpPlayer;
import java.util.ArrayList;
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
        if (mp != null) {
            mp.setLastUpdate(System.currentTimeMillis());
        }
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
    public HashMap<String, MpPlayer> getPlayers() {
        return playerList;
    }

    public synchronized String[] getPlayerListFormatted() {

        HashMap<String, MpPlayer> playersList = getPlayers();

        String[] players = new String[playersList.size()];

        int index = 0;

        for (MpPlayer plr : playersList.values()) {
            players[index] = plr.getUuid();
            index++;
        }

        return players;
    }

    public synchronized void setPlayers(ArrayList<MpPlayer> players) {
        if (playerList == null) {
            playerList = new HashMap<>();
        }
        playerList.clear();
        players.forEach((player) -> {
            playerList.put(player.getUuid(), player);
        });
    }

    public synchronized void setPlayers(HashMap<String, MpPlayer> players) {
        playerList = players;
    }

}
