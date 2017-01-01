/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.entities;

import static com.ahuotala.untitledjavagame.game.Game.spriteSheet;
import com.ahuotala.untitledjavagame.game.Renderer;
import java.awt.Graphics;

/**
 *
 * @author alehuo
 */
public class MpPlayer extends Entity implements java.io.Serializable {

    private int x;
    private int y;
    private Direction direction;
    private String uuid;

    /**
     *
     * @param uuid
     * @param x
     * @param y
     * @param direction
     */
    public MpPlayer(String uuid, int x, int y, Direction direction) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     *
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param g
     * @param r
     * @param player
     */
    public void renderMpPlayer(Graphics g, Renderer r, Player player) {
        switch (this.getDirection()) {
            case UP:
                spriteSheet.paint(r, "player_up", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case DOWN:
                spriteSheet.paint(r, "player_down", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case LEFT:
                spriteSheet.paint(r, "player_left", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case RIGHT:
                spriteSheet.paint(r, "player_right", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            default:
                break;
        }

    }

}
