/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net.packets;

import com.ahuotala.untitledjavagame.entities.Direction;

/**
 *
 * @author alehuo
 */
public class UpdatePositionPacket extends BasicPacket implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    private int x;
    private int y;
    private Direction direction;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    

}
