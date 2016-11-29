/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.net;

import ahuotala.entities.Direction;

/**
 *
 * @author alehuo
 */
public class MpPlayer implements java.io.Serializable {

    private int x;
    private int y;
    private Direction direction;
    private String uuid;

    public MpPlayer(String uuid, int x, int y, Direction direction) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

}
