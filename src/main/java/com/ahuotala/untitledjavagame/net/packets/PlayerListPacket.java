/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net.packets;

import com.ahuotala.untitledjavagame.entities.MpPlayer;
import java.util.ArrayList;

/**
 *
 * @author alehuo
 */
public class PlayerListPacket extends BasicPacket implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MpPlayer> mpPlayers;

    public ArrayList<MpPlayer> getMpPlayers() {
        return mpPlayers;
    }

    public void setMpPlayers(ArrayList<MpPlayer> mpPlayers) {
        this.mpPlayers = mpPlayers;
    }

}
