/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import static ahuotala.game.Game.spriteSheet;
import ahuotala.game.Renderer;
import java.awt.Graphics;

/**
 *
 * @author alehuo
 */
public class MpPlayer implements java.io.Serializable, Entity {

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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
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
