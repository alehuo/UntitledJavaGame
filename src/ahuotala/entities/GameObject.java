package ahuotala.entities;

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
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        width = sprite.getWidth();
        height = sprite.getHeight();
        bounds = new Rectangle(x, y, width, height);
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
        g.drawImage(sprite, x + p.getOffsetX(), y + p.getOffsetY(), null);
    }
    
    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX() - radiusX, y + p.getOffsetY() - radiusY, sprite.getWidth() + 2 * radiusX, sprite.getHeight() + 2 * radiusY, false);
    }
}
