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
package com.alehuo.untitledjavagame.net;

import com.alehuo.untitledjavagame.entities.MpPlayer;
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
