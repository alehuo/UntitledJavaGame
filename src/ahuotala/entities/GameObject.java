/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksi Huotala
 */
public class GameObject {

    private final int x;
    private final int y;
    private final int radiusX = 5;
    private final int radiusY = 5;
    private final BufferedImage sprite;

    public GameObject(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public boolean collidesWithPlayer(Player player) {
        if (player.getX() == x - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
            //Left side
            return true;
        } else if (player.getX() == x + sprite.getWidth() - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
            //Right side
            return true;
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y + sprite.getHeight()) {
            //Bottom side
            return true;
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y - radiusY) {
            //Top side
            return true;
        } else {
            return false;
        }
    }

    public void collide(Player player) {
        if (player.getX() == x - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
            //Left side
            player.setDirectionState(Direction.RIGHT, false);
            player.setDirectionState(Direction.UP, true);
            player.setDirectionState(Direction.DOWN, true);
            player.setDirectionState(Direction.LEFT, true);
        } else if (player.getX() == x + sprite.getWidth() - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
            //Right side
            player.setDirectionState(Direction.UP, true);
            player.setDirectionState(Direction.DOWN, true);
            player.setDirectionState(Direction.LEFT, false);
            player.setDirectionState(Direction.RIGHT, true);
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y + sprite.getHeight()) {
            //Bottom side
            player.setDirectionState(Direction.UP, false);
            player.setDirectionState(Direction.DOWN, true);
            player.setDirectionState(Direction.LEFT, true);
            player.setDirectionState(Direction.RIGHT, true);
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y - radiusY) {
            //Top side
            player.setDirectionState(Direction.UP, true);
            player.setDirectionState(Direction.DOWN, false);
            player.setDirectionState(Direction.LEFT, true);
            player.setDirectionState(Direction.RIGHT, true);
        }
    }

    public void render(Graphics g, Player p) {
        g.drawImage(sprite, x + p.getOffsetX(), y + p.getOffsetY(), null);
        if (this.collidesWithPlayer(p)) {
            this.collide(p);
        } else {
            p.setDirectionState(Direction.UP, true);
            p.setDirectionState(Direction.DOWN, true);
            p.setDirectionState(Direction.LEFT, true);
            p.setDirectionState(Direction.RIGHT, true);
        }
    }

    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX() - radiusX, y + p.getOffsetY() - radiusY, sprite.getWidth() + 2 * radiusX, sprite.getHeight() + 2 * radiusY, false);
    }
}
