/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net.packets;

/**
 * Basic packet abstract class
 * @author alehuo
 */
public abstract class BasicPacket implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Player uuid
     */
    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
