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
            player.setCollisionDirection(Direction.RIGHT);
            return true;
        } else if (player.getX() == x + sprite.getWidth() - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
            //Right side
            player.setCollisionDirection(Direction.LEFT);
            return true;
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y) {
            //Bottom side
            player.setCollisionDirection(Direction.DOWN);
            return true;
        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y + radiusY + sprite.getHeight()) {
            //Top side
            player.setCollisionDirection(Direction.UP);
            return true;
        }
        player.setCollisionDirection(Direction.NAN);
        return false;
    }

    public void render(Graphics g, Player p) {
        g.drawImage(sprite, x + p.getOffsetX(), y + p.getOffsetY(), null);
    }

    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX() - radiusX, y + p.getOffsetY() - radiusY, sprite.getWidth() + 2 * radiusX, sprite.getHeight() + 2 * radiusY, false);
    }
}
