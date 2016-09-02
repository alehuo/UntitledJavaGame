package ahuotala.entities;

import ahuotala.game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksi Huotala
 */
public class GameObject {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int radiusX = 5;
    private final int radiusY = 5;
    private final BufferedImage sprite;
    private final Rectangle bounds;

    public GameObject(int x, int y, BufferedImage sprite) {
        this.x = x * Game.SCALE;
        this.y = y * Game.SCALE;
        this.sprite = sprite;
        width = sprite.getWidth();
        height = sprite.getHeight();
        bounds = new Rectangle(x, y, width * Game.SCALE, height * Game.SCALE);
    }

    public boolean collidesWithPlayer(Player player) {
        if (bounds.intersects(player.getBounds())) {
            player.setX(player.getLastX());
            player.setY(player.getLastY());
            return true;
        }
        player.setLastX(player.getX());
        player.setLastY(player.getY());
        return false;
    }

    public void render(Graphics g, Player p) {
        g.drawImage(sprite, x + p.getOffsetX(), y + p.getOffsetY(), width * Game.SCALE, height * Game.SCALE, null);
    }

    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX(), y + p.getOffsetY(), width * Game.SCALE, height * Game.SCALE, false);
    }
}
